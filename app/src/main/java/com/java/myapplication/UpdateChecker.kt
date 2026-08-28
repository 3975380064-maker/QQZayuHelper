package com.java.myapplication

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.io.File

/**
 * 更新检查器。
 * 通过 GitHub 镜像源获取最新版本号，并用 DownloadManager 下载 APK 安装。
 *
 * 镜像源来自社区维护的测速列表（2026年7月），实际延迟因运营商而异。
 * 策略：先直连，再试第一梯队（<300ms），最后第二梯队（300-600ms）。
 */
object UpdateChecker {

    private const val TAG = "UpdateChecker"
    private const val REPO_OWNER = "3975380064-maker"
    private const val REPO_NAME = "QQZayuHelper"
    private const val APK_FILE = "QQZayuHelper.apk"  // Release 附件名

    /** RAW 文件加速（用于读取 build.gradle.kts 获取版本号） */
    private val RAW_PROXIES = listOf(
        // 直连
        "https://raw.githubusercontent.com/%s/%s/main/app/build.gradle.kts",
        // raw 专用加速
        "https://raw.staticdn.net/%s/%s/main/app/build.gradle.kts",
        "https://githubraw.com/%s/%s/main/app/build.gradle.kts",
        // 前缀代理 + raw
        "https://github.proxy.class3.fun/https://raw.githubusercontent.com/%s/%s/main/app/build.gradle.kts",
        "https://mirror.ghproxy.com/https://raw.githubusercontent.com/%s/%s/main/app/build.gradle.kts",
        "https://gh.jasonzeng.dev/https://raw.githubusercontent.com/%s/%s/main/app/build.gradle.kts",
        "https://git.yylx.win/https://raw.githubusercontent.com/%s/%s/main/app/build.gradle.kts",
        "https://github.geekery.cn/https://raw.githubusercontent.com/%s/%s/main/app/build.gradle.kts",
        "https://gh-proxy.net/https://raw.githubusercontent.com/%s/%s/main/app/build.gradle.kts",
        "https://fastgit.cc/https://raw.githubusercontent.com/%s/%s/main/app/build.gradle.kts",
        // 第二梯队
        "https://github-proxy.com/https://raw.githubusercontent.com/%s/%s/main/app/build.gradle.kts",
        "https://github.limoruirui.com/https://raw.githubusercontent.com/%s/%s/main/app/build.gradle.kts",
        "https://gh.ddlc.top/https://raw.githubusercontent.com/%s/%s/main/app/build.gradle.kts"
    )

    /** 下载源（APK 下载），先直连再加速 */
    private val DOWNLOAD_PROXIES = listOf(
        // 直连
        "https://github.com/%s/%s/releases/latest/download/%s",
        // 完整镜像站（替换 github.com）
        "https://kkgithub.com/%s/%s/releases/latest/download/%s",
        "https://g.nite07.org/%s/%s/releases/latest/download/%s",
        "https://7ed.net/%s/%s/releases/latest/download/%s",
        "https://bgithub.xyz/%s/%s/releases/latest/download/%s",
        // 第一梯队前缀代理（<300ms）
        "https://github.proxy.class3.fun/https://github.com/%s/%s/releases/latest/download/%s",
        "https://mirror.ghproxy.com/https://github.com/%s/%s/releases/latest/download/%s",
        "https://gh.jasonzeng.dev/https://github.com/%s/%s/releases/latest/download/%s",
        "https://tvv.tw/https://github.com/%s/%s/releases/latest/download/%s",
        "https://git.yylx.win/https://github.com/%s/%s/releases/latest/download/%s",
        "https://github.geekery.cn/https://github.com/%s/%s/releases/latest/download/%s",
        "https://ghfile.geekertao.top/https://github.com/%s/%s/releases/latest/download/%s",
        "https://ghproxy.imciel.com/https://github.com/%s/%s/releases/latest/download/%s",
        "https://ghm.078465.xyz/https://github.com/%s/%s/releases/latest/download/%s",
        "https://gh-proxy.net/https://github.com/%s/%s/releases/latest/download/%s",
        "https://gitproxy.click/https://github.com/%s/%s/releases/latest/download/%s",
        "https://ghpxy.hwinzniej.top/https://github.com/%s/%s/releases/latest/download/%s",
        "https://fastgit.cc/https://github.com/%s/%s/releases/latest/download/%s",
        "https://ghproxy.cxkpro.top/https://github.com/%s/%s/releases/latest/download/%s",
        "https://gh.chjina.com/https://github.com/%s/%s/releases/latest/download/%s",
        "https://gitproxy.mrhjx.cn/https://github.com/%s/%s/releases/latest/download/%s",
        "https://ghproxy.cfd/https://github.com/%s/%s/releases/latest/download/%s",
        "https://ghp.keleyaa.com/https://github.com/%s/%s/releases/latest/download/%s",
        "https://g.cachecdn.ggff.net/https://github.com/%s/%s/releases/latest/download/%s",
        "https://github.ednovas.xyz/https://github.com/%s/%s/releases/latest/download/%s",
        "https://gh.llkk.cc/https://github.com/%s/%s/releases/latest/download/%s",
        // 第二梯队前缀代理（300-600ms，备用）
        "https://github.limoruirui.com/https://github.com/%s/%s/releases/latest/download/%s",
        "https://g.blfrp.cn/https://github.com/%s/%s/releases/latest/download/%s",
        "https://gh.ddlc.top/https://github.com/%s/%s/releases/latest/download/%s",
        "https://gp-us.fyan.top/https://github.com/%s/%s/releases/latest/download/%s",
        "https://ghproxy.monkeyray.net/https://github.com/%s/%s/releases/latest/download/%s",
        "https://hub.gitmirror.com/https://github.com/%s/%s/releases/latest/download/%s",
        "https://github-proxy.com/https://github.com/%s/%s/releases/latest/download/%s"
    )

    private var downloadId: Long = -1L
    private var downloadStartTime: Long = 0L
    private var downloadAttemptIndex = -1
    private var downloadReceiver: BroadcastReceiver? = null
    private var pendingOnStart: (() -> Unit)? = null
    private var pendingOnComplete: ((Boolean) -> Unit)? = null
    private const val DOWNLOAD_TIMEOUT_MS = 120_000L  // 2 分钟无广播则超时重置
    private const val REACHABLE_TIMEOUT_MS = 5000L     // HEAD 预检超时

    /**
     * 检查是否有新版本（同步阻塞，在后台线程调用）。
     * @return Pair(hasNewVersion, latestVersionName) 或 null（检查失败）
     */
    fun checkUpdate(context: Context): Pair<Boolean, String>? {
        val currentVersion = getCurrentVersion(context)
        val latestVersion = fetchLatestVersion() ?: return null
        return Pair(latestVersion != currentVersion && compareVersions(latestVersion, currentVersion) > 0, latestVersion)
    }

    /**
     * 下载最新 APK。
     * 先对每个源做 HEAD 预检，不可达立即跳过；入队后若下载失败自动换下一个源重试。
     */
    fun downloadUpdate(context: Context, onStart: () -> Unit, onComplete: (Boolean) -> Unit) {
        // 检查是否有卡死的下载任务
        if (downloadId != -1L) {
            if (downloadStartTime > 0 && System.currentTimeMillis() - downloadStartTime > DOWNLOAD_TIMEOUT_MS) {
                android.util.Log.w(TAG, "下载任务超时，重置")
                downloadId = -1L
                downloadStartTime = 0L
            } else {
                return  // 已有下载任务进行中
            }
        }

        pendingOnStart = onStart
        pendingOnComplete = onComplete
        startDownload(context, 0)
    }

    /** 从指定索引开始，预检并启动下载 */
    private fun startDownload(context: Context, startIndex: Int) {
        // 先注册下载完成广播（每次尝试都注册新的）
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(ctx: Context, intent: Intent) {
                val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L)
                if (id != downloadId) return
                ctx.unregisterReceiver(this)
                downloadReceiver = null

                // 检查下载状态
                val dm = ctx.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                val query = DownloadManager.Query().setFilterById(id)
                val cursor = dm.query(query)
                var success = false
                if (cursor.moveToFirst()) {
                    val status = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS))
                    if (status == DownloadManager.STATUS_SUCCESSFUL) {
                        success = true
                        val uriStr = cursor.getString(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_LOCAL_URI))
                        installApk(ctx, uriStr)
                    }
                }
                cursor.close()

                if (success) {
                    downloadId = -1L
                    downloadStartTime = 0L
                    downloadAttemptIndex = -1
                    pendingOnComplete?.invoke(true)
                } else {
                    // 下载失败，尝试下一个源
                    android.util.Log.w(TAG, "下载失败（源索引 $downloadAttemptIndex），尝试下一个")
                    downloadId = -1L
                    downloadStartTime = 0L
                    val nextIndex = downloadAttemptIndex + 1
                    if (nextIndex < DOWNLOAD_PROXIES.size) {
                        startDownload(ctx, nextIndex)
                    } else {
                        downloadAttemptIndex = -1
                        pendingOnComplete?.invoke(false)
                        Toast.makeText(ctx, "所有下载源都不可用，请手动访问 GitHub 下载", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        downloadReceiver = receiver
        try {
            context.registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE),
                Context.RECEIVER_NOT_EXPORTED)
        } catch (e: Exception) {
            android.util.Log.w(TAG, "注册下载完成广播失败", e)
            pendingOnComplete?.invoke(false)
            return
        }

        // HEAD 预检找第一个可达的源
        for (i in startIndex until DOWNLOAD_PROXIES.size) {
            val url = DOWNLOAD_PROXIES[i].format(REPO_OWNER, REPO_NAME, APK_FILE)
            if (!isReachable(url)) {
                android.util.Log.w(TAG, "源不可达，跳过: ${DOWNLOAD_PROXIES[i]}")
                continue
            }

            try {
                val request = DownloadManager.Request(Uri.parse(url))
                request.setTitle("杂鱼助手更新")
                request.setDescription("正在下载新版本...")
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "ZayuHelper/ZayuHelper_update.apk")
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)

                val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                downloadId = dm.enqueue(request)
                downloadStartTime = System.currentTimeMillis()
                downloadAttemptIndex = i
                pendingOnStart?.invoke()
                return
            } catch (e: Exception) {
                android.util.Log.w(TAG, "enqueue 失败: ${DOWNLOAD_PROXIES[i]}", e)
            }
        }

        // 全部不可达
        unregisterDownloadReceiver(context)
        pendingOnComplete?.invoke(false)
        Toast.makeText(context, "所有下载源都不可用，请手动访问 GitHub 下载", Toast.LENGTH_LONG).show()
    }

    /** HEAD 预检：URL 是否可达 */
    private fun isReachable(url: String): Boolean {
        var connection: HttpURLConnection? = null
        try {
            connection = URL(url).openConnection() as HttpURLConnection
            connection.requestMethod = "HEAD"
            connection.connectTimeout = REACHABLE_TIMEOUT_MS.toInt()
            connection.readTimeout = REACHABLE_TIMEOUT_MS.toInt()
            connection.instanceFollowRedirects = true
            val code = connection.responseCode
            return code in 200..399
        } catch (e: Exception) {
            android.util.Log.w(TAG, "预检失败: $url", e)
            return false
        } finally {
            connection?.disconnect()
        }
    }

    private fun unregisterDownloadReceiver(context: Context) {
        downloadReceiver?.let {
            try { context.unregisterReceiver(it) } catch (_: Exception) {}
        }
        downloadReceiver = null
    }

    /**
     * 检查是否已授予安装未知应用权限（Android 8.0+）。
     */
    fun canRequestInstallPackages(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.packageManager.canRequestPackageInstalls()
        } else {
            true // Android 8.0 以下不需要此权限
        }
    }

    /**
     * 跳转到系统设置开启"安装未知应用"权限。
     */
    fun openInstallPermissionSettings(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES).apply {
                data = Uri.parse("package:${context.packageName}")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        }
    }

    private fun installApk(context: Context, uriStr: String) {
        // 注册 MY_PACKAGE_REPLACED 广播：覆盖安装完成后删除源文件
        // 自更新（覆盖安装）触发的是 ACTION_MY_PACKAGE_REPLACED，不是 PACKAGE_ADDED
        val installReceiver = object : BroadcastReceiver() {
            override fun onReceive(ctx: Context, intent: Intent) {
                ctx.unregisterReceiver(this)
                deleteDownloadedFile(context)
            }
        }
        try {
            context.registerReceiver(installReceiver,
                IntentFilter(Intent.ACTION_MY_PACKAGE_REPLACED),
                Context.RECEIVER_NOT_EXPORTED)
        } catch (e: Exception) {
            android.util.Log.w(TAG, "注册安装完成广播失败", e)
            return
        }

        try {
            // 直接走 FileProvider，避免 file:// URI 在 Android 7.0+ 抛异常
            val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "ZayuHelper/ZayuHelper_update.apk")
            if (file.exists()) {
                val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    setDataAndType(uri, "application/vnd.android.package-archive")
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                context.startActivity(intent)
            } else {
                // 兜底：用 DownloadManager 返回的 URI
                val uri = Uri.parse(uriStr)
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    setDataAndType(uri, "application/vnd.android.package-archive")
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    if (uri.scheme == "file") {
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    }
                }
                context.startActivity(intent)
            }
        } catch (e: Exception) {
            android.util.Log.w(TAG, "安装失败", e)
            Toast.makeText(context, "安装失败，请手动打开下载目录安装", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * 删除已下载的 APK 文件。
     */
    private fun deleteDownloadedFile(context: Context) {
        try {
            val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "ZayuHelper/ZayuHelper_update.apk")
            if (file.exists()) {
                file.delete()
                android.util.Log.i(TAG, "已删除下载的 APK 文件")
            }
        } catch (e: Exception) {
            android.util.Log.w(TAG, "删除 APK 文件失败", e)
        }
    }

    private fun getCurrentVersion(context: Context): String {
        try {
            return context.packageManager.getPackageInfo(context.packageName, 0).versionName ?: "0.0.0"
        } catch (e: Exception) {
            return "0.0.0"
        }
    }

    /**
     * 从镜像源读取 build.gradle.kts 获取最新版本号。
     */
    private fun fetchLatestVersion(): String? {
        for (proxy in RAW_PROXIES) {
            var connection: HttpURLConnection? = null
            try {
                val url = proxy.format(REPO_OWNER, REPO_NAME)
                connection = URL(url).openConnection() as HttpURLConnection
                connection.connectTimeout = 8000
                connection.readTimeout = 8000
                connection.instanceFollowRedirects = true
                connection.requestMethod = "GET"

                if (connection.responseCode == 200) {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val content = reader.readText()
                    reader.close()

                    // 从 build.gradle.kts 中提取 versionName
                    val regex = Regex("""versionName\s*=\s*"([^"]+)"""")
                    val match = regex.find(content)
                    if (match != null) {
                        return match.groupValues[1]
                    }
                }
            } catch (e: Exception) {
                android.util.Log.w(TAG, "RAW 源失败: $proxy", e)
            } finally {
                connection?.disconnect()
            }
        }
        return null
    }

    /**
     * 比较版本号，version1 > version2 返回正数。
     */
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