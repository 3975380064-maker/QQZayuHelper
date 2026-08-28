package com.java.myapplication

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

/**
 * 更新检查器。
 * 通过 GitHub 镜像源获取最新版本号，并用直连 HTTP 下载 APK 后安装。
 * 不依赖 DownloadManager 系统广播，避免 content:// URI 权限问题。
 */
object UpdateChecker {

    private const val TAG = "UpdateChecker"
    private const val REPO_OWNER = "3975380064-maker"
    private const val REPO_NAME = "QQZayuHelper"
    private const val APK_FILE = "QQZayuHelper.apk"

    private val DOWNLOAD_PROXIES = listOf(
        "https://github.com/%s/%s/releases/latest/download/%s",
        "https://kkgithub.com/%s/%s/releases/latest/download/%s",
        "https://g.nite07.org/%s/%s/releases/latest/download/%s",
        "https://7ed.net/%s/%s/releases/latest/download/%s",
        "https://bgithub.xyz/%s/%s/releases/latest/download/%s",
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
        "https://github.limoruirui.com/https://github.com/%s/%s/releases/latest/download/%s",
        "https://g.blfrp.cn/https://github.com/%s/%s/releases/latest/download/%s",
        "https://gh.ddlc.top/https://github.com/%s/%s/releases/latest/download/%s",
        "https://gp-us.fyan.top/https://github.com/%s/%s/releases/latest/download/%s",
        "https://ghproxy.monkeyray.net/https://github.com/%s/%s/releases/latest/download/%s",
        "https://hub.gitmirror.com/https://github.com/%s/%s/releases/latest/download/%s",
        "https://github-proxy.com/https://github.com/%s/%s/releases/latest/download/%s"
    )

    private val RAW_PROXIES = listOf(
        "https://raw.githubusercontent.com/%s/%s/main/app/build.gradle.kts",
        "https://raw.staticdn.net/%s/%s/main/app/build.gradle.kts",
        "https://githubraw.com/%s/%s/main/app/build.gradle.kts",
        "https://github.proxy.class3.fun/https://raw.githubusercontent.com/%s/%s/main/app/build.gradle.kts",
        "https://mirror.ghproxy.com/https://raw.githubusercontent.com/%s/%s/main/app/build.gradle.kts",
        "https://gh.jasonzeng.dev/https://raw.githubusercontent.com/%s/%s/main/app/build.gradle.kts",
        "https://git.yylx.win/https://raw.githubusercontent.com/%s/%s/main/app/build.gradle.kts",
        "https://github.geekery.cn/https://raw.githubusercontent.com/%s/%s/main/app/build.gradle.kts",
        "https://gh-proxy.net/https://raw.githubusercontent.com/%s/%s/main/app/build.gradle.kts",
        "https://fastgit.cc/https://raw.githubusercontent.com/%s/%s/main/app/build.gradle.kts",
        "https://github-proxy.com/https://raw.githubusercontent.com/%s/%s/main/app/build.gradle.kts",
        "https://github.limoruirui.com/https://raw.githubusercontent.com/%s/%s/main/app/build.gradle.kts",
        "https://gh.ddlc.top/https://raw.githubusercontent.com/%s/%s/main/app/build.gradle.kts"
    )

    private val downloadDir: File
        get() = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "ZayuHelper")

    private val apkFile: File
        get() = File(downloadDir, "ZayuHelper_update.apk")

    private var isDownloading = false

    /**
     * 检查是否有新版本（同步阻塞，在后台线程调用）。
     */
    fun checkUpdate(context: Context): Pair<Boolean, String>? {
        val currentVersion = getCurrentVersion(context)
        val latestVersion = fetchLatestVersion() ?: return null
        return Pair(latestVersion != currentVersion && compareVersions(latestVersion, currentVersion) > 0, latestVersion)
    }

    /**
     * 下载最新 APK。在后台线程执行，完成后自动安装。
     */
    fun downloadUpdate(context: Context, onStart: () -> Unit, onComplete: (Boolean) -> Unit) {
        if (isDownloading) {
            Toast.makeText(context, "下载中，请稍候...", Toast.LENGTH_SHORT).show()
            return
        }

        // 确保下载目录存在
        try {
            downloadDir.mkdirs()
        } catch (e: Exception) {
            android.util.Log.w(TAG, "创建目录失败", e)
        }

        // 删除旧文件
        try {
            if (apkFile.exists()) apkFile.delete()
        } catch (e: Exception) {
            android.util.Log.w(TAG, "删除旧文件失败", e)
        }

        isDownloading = true
        onStart()

        Thread {
            var success = false
            try {
                for (i in DOWNLOAD_PROXIES.indices) {
                    val urlStr = DOWNLOAD_PROXIES[i].format(REPO_OWNER, REPO_NAME, APK_FILE)
                    try {
                        android.util.Log.i(TAG, "尝试下载源 $i: $urlStr")
                        if (httpDownload(urlStr, apkFile)) {
                            if (isValidApk(apkFile)) {
                                android.util.Log.i(TAG, "下载成功，源索引 $i")
                                success = true
                                break
                            } else {
                                android.util.Log.w(TAG, "文件不是合法 APK，尝试下一个源")
                                apkFile.delete()
                            }
                        }
                    } catch (e: Exception) {
                        android.util.Log.w(TAG, "源 $i 下载失败: ${e.message}")
                    }
                }
            } catch (e: Exception) {
                android.util.Log.w(TAG, "下载异常", e)
            }

            val result = success
            isDownloading = false

            if (result) {
                // 切回主线程安装
                android.os.Handler(android.os.Looper.getMainLooper()).post {
                    installApk(context)
                }
            }

            android.os.Handler(android.os.Looper.getMainLooper()).post {
                onComplete(result)
                if (!result) {
                    Toast.makeText(context, "下载失败，请手动访问 GitHub 下载", Toast.LENGTH_LONG).show()
                }
            }
        }.start()
    }

    /** HTTP 下载文件到本地 */
    private fun httpDownload(urlStr: String, dest: File): Boolean {
        var connection: HttpURLConnection? = null
        var input: InputStream? = null
        var output: FileOutputStream? = null
        try {
            connection = URL(urlStr).openConnection() as HttpURLConnection
            connection.connectTimeout = 15000
            connection.readTimeout = 30000
            connection.instanceFollowRedirects = true
            connection.requestMethod = "GET"

            val code = connection.responseCode
            if (code !in 200..399) {
                android.util.Log.w(TAG, "HTTP $code: $urlStr")
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
            }
            output.flush()

            android.util.Log.i(TAG, "下载完成: ${totalRead} bytes")
            return totalRead > 1024
        } catch (e: Exception) {
            android.util.Log.w(TAG, "HTTP 下载异常: $urlStr", e)
            return false
        } finally {
            try { input?.close() } catch (_: Exception) {}
            try { output?.close() } catch (_: Exception) {}
            try { connection?.disconnect() } catch (_: Exception) {}
        }
    }

    /** 验证文件是否为合法 APK（检查 ZIP 魔数 PK） */
    private fun isValidApk(file: File): Boolean {
        if (!file.exists() || file.length() < 1024) return false
        try {
            val fis = java.io.FileInputStream(file)
            val magic = ByteArray(2)
            fis.read(magic)
            fis.close()
            return magic[0] == 0x50.toByte() && magic[1] == 0x4B.toByte()
        } catch (e: Exception) {
            return false
        }
    }

    /** 安装 APK（通过 FileProvider） */
    private fun installApk(context: Context) {
        try {
            if (!apkFile.exists()) {
                android.util.Log.w(TAG, "APK 文件不存在")
                return
            }

            val installReceiver = object : android.content.BroadcastReceiver() {
                override fun onReceive(ctx: Context, intent: Intent) {
                    ctx.unregisterReceiver(this)
                    try { apkFile.delete() } catch (_: Exception) {}
                }
            }
            try {
                context.registerReceiver(installReceiver,
                    android.content.IntentFilter(android.content.Intent.ACTION_MY_PACKAGE_REPLACED),
                    Context.RECEIVER_NOT_EXPORTED)
            } catch (e: Exception) {
                android.util.Log.w(TAG, "注册安装完成广播失败", e)
            }

            val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", apkFile)
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, "application/vnd.android.package-archive")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            android.util.Log.w(TAG, "安装失败", e)
            Toast.makeText(context, "安装失败，请手动打开 Download/ZayuHelper/ 目录安装", Toast.LENGTH_LONG).show()
        }
    }

    fun canRequestInstallPackages(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.packageManager.canRequestPackageInstalls()
        } else {
            true
        }
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

    private fun getCurrentVersion(context: Context): String {
        return try {
            context.packageManager.getPackageInfo(context.packageName, 0).versionName ?: "0.0.0"
        } catch (e: Exception) {
            "0.0.0"
        }
    }

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
                    val reader = java.io.BufferedReader(java.io.InputStreamReader(connection.inputStream))
                    val content = reader.readText()
                    reader.close()
                    val regex = Regex("""versionName\s*=\s*"([^"]+)"""")
                    val match = regex.find(content)
                    if (match != null) return match.groupValues[1]
                }
            } catch (e: Exception) {
                android.util.Log.w(TAG, "RAW 源失败: $proxy", e)
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