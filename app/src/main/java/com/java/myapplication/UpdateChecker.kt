package com.java.myapplication

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * 更新检查器。
 * 通过 GitHub 镜像源获取最新版本号，并用 DownloadManager 下载 APK 安装。
 */
object UpdateChecker {

    private const val TAG = "UpdateChecker"
    private const val REPO_OWNER = "3975380064-maker"
    private const val REPO_NAME = "QQZayuHelper"
    private const val APK_FILE = "QQZayuHelper_v1.2.apk"  // 文件名固定，Release 会更新

    /** 镜像源列表，按优先级排列 */
    private val MIRRORS = listOf(
        "https://hub.gitmirror.com/https://github.com/%s/%s/releases/latest/download/%s",
        "https://gh.llkk.cc/https://github.com/%s/%s/releases/latest/download/%s",
        "https://ghproxy.com/https://github.com/%s/%s/releases/latest/download/%s",
        "https://shturl.cc/ohz3uCOnx/https://github.com/%s/%s/releases/latest/download/%s",
        "https://shturl.cc/H/https://github.com/%s/%s/releases/latest/download/%s"
    )

    /** API 镜像源（用于获取版本信息） */
    private val API_MIRRORS = listOf(
        "https://hub.gitmirror.com/https://raw.githubusercontent.com/%s/%s/master/app/build.gradle.kts",
        "https://gh.llkk.cc/https://raw.githubusercontent.com/%s/%s/master/app/build.gradle.kts",
        "https://ghproxy.com/https://raw.githubusercontent.com/%s/%s/master/app/build.gradle.kts"
    )

    private var downloadId: Long = -1L

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
     */
    fun downloadUpdate(context: Context, onStart: () -> Unit, onComplete: (Boolean) -> Unit) {
        if (downloadId != -1L) {
            // 已有下载任务
            return
        }

        // 注册下载完成广播
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(ctx: Context, intent: Intent) {
                val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L)
                if (id == downloadId) {
                    ctx.unregisterReceiver(this)
                    downloadId = -1L
                    onComplete(true)
                    // 启动安装
                    val dm = ctx.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                    val query = DownloadManager.Query().setFilterById(id)
                    val cursor = dm.query(query)
                    if (cursor.moveToFirst()) {
                        val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                        if (status == DownloadManager.STATUS_SUCCESSFUL) {
                            val uri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI))
                            installApk(ctx, Uri.parse(uri))
                        }
                    }
                    cursor.close()
                }
            }
        }
        context.registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE),
            Context.RECEIVER_EXPORTED)

        // 遍历镜像源尝试下载
        for (mirror in MIRRORS) {
            try {
                val url = mirror.format(REPO_OWNER, REPO_NAME, APK_FILE)
                val request = DownloadManager.Request(Uri.parse(url))
                request.setTitle("杂鱼助手更新")
                request.setDescription("正在下载新版本...")
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "ZayuHelper_update.apk")
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)

                val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                downloadId = dm.enqueue(request)
                onStart()
                return
            } catch (e: Exception) {
                android.util.Log.w(TAG, "镜像下载失败: $mirror", e)
            }
        }

        onComplete(false)
        Toast.makeText(context, "所有镜像源都不可用，请手动访问 GitHub 下载", Toast.LENGTH_LONG).show()
    }

    private fun installApk(context: Context, uri: Uri) {
        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, "application/vnd.android.package-archive")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            android.util.Log.w(TAG, "安装失败", e)
            Toast.makeText(context, "安装失败，请手动打开下载目录安装", Toast.LENGTH_LONG).show()
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
        for (mirror in API_MIRRORS) {
            try {
                val url = mirror.format(REPO_OWNER, REPO_NAME)
                val connection = URL(url).openConnection() as HttpURLConnection
                connection.connectTimeout = 8000
                connection.readTimeout = 8000
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
                connection.disconnect()
            } catch (e: Exception) {
                android.util.Log.w(TAG, "API 镜像失败: $mirror", e)
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