package com.java.myapplication

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

/**
 * 更新检查器。
 *
 * 设计前提：GitHub 官方源在国内不稳定，所以版本探测和 APK 下载都允许走第三方镜像。
 * 正因如此，**镜像返回的文件一律视为不可信**，安装前必须通过 [runDownload] 里的校验：
 *   1. 能解析出 PackageInfo（被镜像截断的包会在这里被拒，实测 ghproxy.net 就会返回截断文件）
 *   2. 包名与自身一致
 *   3. 签名与当前已安装版本完全一致（防镜像投毒 / 中间人换成自己的包）
 *   4. versionCode 高于当前版本
 * 任一条不过就删掉文件、换下一个源；所有源都不过则不安装。
 * 好处是：镜像列表可以随便增删，安全性不依赖“信任某个镜像”。
 *
 * 其它约定：
 *   - 不用 DownloadManager（content:// 权限问题），用 HttpURLConnection 直接下载。
 *   - 不用 ACTION_MY_PACKAGE_REPLACED 清理文件：该广播由替换后的新进程接收，
 *     注册它的旧进程此时已被系统杀掉，根本收不到。
 *   - APK 落在 getExternalFilesDir()/updates：不需要任何存储权限，也不受分区存储限制。
 */
object UpdateChecker {

    private const val TAG = "UpdateChecker"
    private const val REPO_OWNER = "3975380064-maker"
    private const val REPO_NAME = "QQZayuHelper"
    private const val APK_ASSET_NAME = "QQZayuHelper.apk"

    /** 认为合法的 APK 至少要有这么大，用来拦掉镜像返回的错误页 / 截断文件。 */
    private const val MIN_APK_SIZE = 512L * 1024L

    /** 上限，防止异常响应把存储写满。 */
    private const val MAX_APK_SIZE = 128L * 1024L * 1024L

    /** 单个源的连接/读取超时，失败要快速跳过，不能让用户干等。 */
    private const val CONNECT_TIMEOUT_MS = 8000
    private const val READ_TIMEOUT_MS = 20000

    /** 所有源合计的时间预算，超了就放弃，避免长时间卡在“下载中”。 */
    private const val DOWNLOAD_BUDGET_MS = 150_000L

    /**
     * 下载源。
     *
     * 注意：镜像的可用性随地区、运营商、时间变化很大，在这里测通不代表别处能用，
     * 反过来也一样（实测同一个 fastgit.cc，两次探测结果就一死一活）。
     * 所以这里刻意覆盖多个不同运营方的镜像，而不是只留“当前测得最快”的几个 ——
     * 多加一个源最坏只是多花几秒重试，安全性由 verifyApk 的签名校验兜底。
     *
     * 顺序按实测结果排：官方直连优先，其余按可用性依次降级。
     * 实测（2026-09-30，单次探测）可用：github.com 直连、gh.xmly.dev、gh-proxy.com、
     * ghproxy.net、gh-proxy.net、gitproxy.click、fastgit.cc、gh.chjina.com、
     * ghp.keleyaa.com、gh.ddlc.top（返回 429，限流但主机可达）。
     */
    private val DOWNLOAD_SOURCES = listOf(
        "https://github.com/%s/%s/releases/latest/download/%s",
        "https://gh.xmly.dev/https://github.com/%s/%s/releases/latest/download/%s",
        "https://gh-proxy.com/https://github.com/%s/%s/releases/latest/download/%s",
        "https://ghproxy.net/https://github.com/%s/%s/releases/latest/download/%s",
        "https://gh-proxy.net/https://github.com/%s/%s/releases/latest/download/%s",
        "https://gitproxy.click/https://github.com/%s/%s/releases/latest/download/%s",
        "https://fastgit.cc/https://github.com/%s/%s/releases/latest/download/%s",
        "https://gh.chjina.com/https://github.com/%s/%s/releases/latest/download/%s",
        "https://ghp.keleyaa.com/https://github.com/%s/%s/releases/latest/download/%s",
        "https://gh.ddlc.top/https://github.com/%s/%s/releases/latest/download/%s"
    )

    /**
     * 版本探测源：读取仓库里的 app/build.gradle.kts 解析 versionName。
     * 用文件而不是 GitHub API，因为 api.github.com 在真实网络下常被限流（实测 403）。
     * jsDelivr 放最后：它是 CDN 缓存，可能给出最多 12 小时前的旧版本号。
     */
    private val VERSION_SOURCES = listOf(
        "https://raw.githubusercontent.com/%s/%s/main/app/build.gradle.kts",
        "https://gh-proxy.com/https://raw.githubusercontent.com/%s/%s/main/app/build.gradle.kts",
        "https://ghproxy.net/https://raw.githubusercontent.com/%s/%s/main/app/build.gradle.kts",
        "https://gh.xmly.dev/https://raw.githubusercontent.com/%s/%s/main/app/build.gradle.kts",
        "https://githubraw.com/%s/%s/main/app/build.gradle.kts",
        "https://cdn.jsdelivr.net/gh/%s/%s@main/app/build.gradle.kts"
    )

    private val VERSION_NAME_REGEX = Regex("""versionName\s*=\s*"([^"]+)"""")

    private var isDownloading = false

    /** 版本检查结果。 */
    data class VersionCheck(val hasUpdate: Boolean, val latestVersion: String)

    /** 下载结果，供界面区分提示文案。 */
    enum class DownloadResult {
        /** 校验通过，已拉起安装界面 */
        SUCCESS,

        /** 下载到的包不比当前新（一般是版本探测超前于实际发布） */
        ALREADY_LATEST,

        /** 下载到了东西，但没通过校验（截断 / 包名不符 / 签名不符） */
        REJECTED,

        /** 所有源都拿不到文件 */
        FAILED
    }

    /** getExternalFilesDir 在外部存储不可用时会返回 null，退回内部目录，避免更新功能静默失效。 */
    private fun updateDir(context: Context): File =
        File(context.getExternalFilesDir(null) ?: context.filesDir, "updates")

    private fun apkFile(context: Context): File =
        File(updateDir(context), "ZayuHelper_update.apk")

    /** 清理上一次遗留的更新包，App 启动时调用。 */
    fun cleanUp(context: Context) {
        try {
            updateDir(context).listFiles()?.forEach { it.delete() }
        } catch (e: Exception) {
            Log.w(TAG, "清理更新缓存失败", e)
        }
    }

    /** 检查是否有新版本，会阻塞网络，必须在后台线程调用。返回 null 表示检查失败。 */
    fun checkUpdate(context: Context): VersionCheck? {
        val currentVersion = getInstalledVersionName(context)
        val latestVersion = fetchLatestVersion() ?: return null
        return VersionCheck(compareVersions(latestVersion, currentVersion) > 0, latestVersion)
    }

    /**
     * 下载并安装最新版。全程在后台线程执行，回调切回主线程。
     * 校验通过才会拉起系统安装界面。
     */
    fun downloadUpdate(context: Context, onStart: () -> Unit, onComplete: (DownloadResult) -> Unit) {
        if (isDownloading) {
            Toast.makeText(context, "下载中，请稍候...", Toast.LENGTH_SHORT).show()
            return
        }
        isDownloading = true
        onStart()

        Thread {
            val result = try {
                runDownload(context)
            } catch (e: Exception) {
                Log.w(TAG, "下载异常", e)
                DownloadResult.FAILED
            } finally {
                isDownloading = false
            }
            Handler(Looper.getMainLooper()).post {
                if (result == DownloadResult.SUCCESS) {
                    installApk(context)
                }
                onComplete(result)
            }
        }.start()
    }

    private fun runDownload(context: Context): DownloadResult {
        val pm = context.packageManager
        val installed = ApkVerifier.installedInfo(context.packageManager, context.packageName) ?: run {
            Log.w(TAG, "读取当前版本信息失败")
            return DownloadResult.FAILED
        }

        val dir = updateDir(context)
        if (!dir.exists() && !dir.mkdirs()) {
            Log.w(TAG, "无法创建更新目录: $dir")
            return DownloadResult.FAILED
        }

        val target = apkFile(context)
        var sawNotNewer = false
        var sawInvalid = false
        val deadline = System.currentTimeMillis() + DOWNLOAD_BUDGET_MS

        for ((index, template) in DOWNLOAD_SOURCES.withIndex()) {
            if (System.currentTimeMillis() > deadline) {
                Log.w(TAG, "已用完 ${DOWNLOAD_BUDGET_MS}ms 预算，停止尝试剩余源")
                break
            }
            val url = template.format(REPO_OWNER, REPO_NAME, APK_ASSET_NAME)
            if (target.exists() && !target.delete()) {
                Log.w(TAG, "无法删除旧文件，跳过本次更新")
                return DownloadResult.FAILED
            }
            Log.i(TAG, "尝试源[$index] $url")
            if (!httpDownload(url, target)) {
                continue
            }

            val candidate = ApkVerifier.readPackageInfo(pm, target)
            if (candidate == null || candidate.packageName != context.packageName) {
                Log.w(TAG, "源[$index] 不是本应用的合法 APK，丢弃")
                sawInvalid = true
                target.delete()
                continue
            }
            if (!ApkVerifier.hasSameSigner(installed, candidate)) {
                Log.w(TAG, "源[$index] 签名与当前版本不一致，拒绝安装（疑似镜像投毒）")
                sawInvalid = true
                target.delete()
                continue
            }
            if (!ApkVerifier.isNewer(candidate, installed)) {
                Log.i(TAG, "源[$index] 版本未高于当前（${candidate.versionName}），无需安装")
                sawNotNewer = true
                target.delete()
                continue
            }

            Log.i(
                TAG,
                "源[$index] 校验通过：${candidate.versionName}(${ApkVerifier.versionCodeOf(candidate)})"
            )
            return DownloadResult.SUCCESS
        }

        // 失败路径不要留下半截文件
        if (target.exists()) target.delete()

        return when {
            sawNotNewer -> DownloadResult.ALREADY_LATEST
            sawInvalid -> DownloadResult.REJECTED
            else -> DownloadResult.FAILED
        }
    }

    private fun httpDownload(urlStr: String, dest: File): Boolean {
        var connection: HttpURLConnection? = null
        var input: InputStream? = null
        var output: FileOutputStream? = null
        try {
            connection = URL(urlStr).openConnection() as HttpURLConnection
            connection.connectTimeout = CONNECT_TIMEOUT_MS
            connection.readTimeout = READ_TIMEOUT_MS
            connection.instanceFollowRedirects = true
            connection.requestMethod = "GET"
            // 不要压缩，保证按字节读取，便于判断文件是否被截断
            connection.setRequestProperty("Accept-Encoding", "identity")

            val code = connection.responseCode
            if (code != HttpURLConnection.HTTP_OK) {
                Log.w(TAG, "HTTP $code: $urlStr")
                return false
            }

            input = connection.inputStream
            output = FileOutputStream(dest)
            val buffer = ByteArray(8192)
            var bytesRead: Int
            var totalRead = 0L
            while (input.read(buffer).also { bytesRead = it } != -1) {
                output.write(buffer, 0, bytesRead)
                totalRead += bytesRead
                if (totalRead > MAX_APK_SIZE) {
                    Log.w(TAG, "响应体异常变大，放弃: $urlStr")
                    return false
                }
            }
            output.flush()
            Log.i(TAG, "下载完成 ${totalRead}B: $urlStr")

            if (totalRead < MIN_APK_SIZE) {
                Log.w(TAG, "文件过小，疑似被镜像截断（${totalRead}B）")
                return false
            }
            if (!isZipFile(dest)) {
                Log.w(TAG, "不是 ZIP/APK 文件: $urlStr")
                return false
            }
            return true
        } catch (e: Exception) {
            Log.w(TAG, "HTTP 下载异常: $urlStr", e)
            return false
        } finally {
            try { input?.close() } catch (_: Exception) {}
            try { output?.close() } catch (_: Exception) {}
            try { connection?.disconnect() } catch (_: Exception) {}
        }
    }

    /** 校验 ZIP/APK 魔数（PK）。只是第一道粗筛，真正的把关在签名校验。 */
    private fun isZipFile(file: File): Boolean {
        if (!file.exists() || file.length() < MIN_APK_SIZE) return false
        return try {
            val magic = ByteArray(4)
            java.io.FileInputStream(file).use { fis ->
                if (fis.read(magic) != 4) return false
            }
            magic[0] == 0x50.toByte() && magic[1] == 0x4B.toByte()
        } catch (e: Exception) {
            Log.w(TAG, "读取文件头失败", e)
            false
        }
    }

    private fun installApk(context: Context) {
        try {
            val file = apkFile(context)
            if (!file.exists()) {
                Log.w(TAG, "APK 文件不存在")
                return
            }
            val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, "application/vnd.android.package-archive")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Log.w(TAG, "拉起安装界面失败", e)
            Toast.makeText(context, "无法拉起安装界面，请稍后重试", Toast.LENGTH_LONG).show()
        }
    }

    fun canRequestInstallPackages(context: Context): Boolean =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.packageManager.canRequestPackageInstalls()
        } else {
            true
        }

    fun openInstallPermissionSettings(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES).apply {
                data = Uri.parse("package:${context.packageName}")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        }
    }

    private fun getInstalledVersionName(context: Context): String = try {
        @Suppress("DEPRECATION")
        context.packageManager.getPackageInfo(context.packageName, 0).versionName ?: "0.0.0"
    } catch (e: Exception) {
        Log.w(TAG, "读取当前版本号失败", e)
        "0.0.0"
    }

    /** 探测最新版本号。任一个源成功即返回，全部失败返回 null。 */
    private fun fetchLatestVersion(): String? {
        for (template in VERSION_SOURCES) {
            var connection: HttpURLConnection? = null
            try {
                val url = template.format(REPO_OWNER, REPO_NAME)
                connection = URL(url).openConnection() as HttpURLConnection
                connection.connectTimeout = 8000
                connection.readTimeout = 8000
                connection.instanceFollowRedirects = true
                connection.requestMethod = "GET"
                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    val content = connection.inputStream.bufferedReader().use { it.readText() }
                    val version = VERSION_NAME_REGEX.find(content)?.groupValues?.get(1)
                    if (!version.isNullOrBlank()) {
                        Log.i(TAG, "最新版本 $version（来源 $url）")
                        return version
                    }
                }
            } catch (e: Exception) {
                Log.w(TAG, "版本源失败: $template", e)
            } finally {
                connection?.disconnect()
            }
        }
        return null
    }

    private fun compareVersions(v1: String, v2: String): Int {
        val parts1 = v1.split(".").map { it.toIntOrNull() ?: 0 }
        val parts2 = v2.split(".").map { it.toIntOrNull() ?: 0 }
        val maxLen = maxOf(parts1.size, parts2.size)
        for (i in 0 until maxLen) {
            val p1 = parts1.getOrElse(i) { 0 }
            val p2 = parts2.getOrElse(i) { 0 }
            if (p1 != p2) return p1 - p2
        }
        return 0
    }
}