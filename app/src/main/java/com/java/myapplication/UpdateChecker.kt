package com.java.myapplication

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Environment
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
                            installApk(ctx)
                        }
                    }
                    cursor.close()
                }
            }
        }
        context.registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE),
            Context.RECEIVER_EXPORTED)

        // 遍历下载源尝试
        for (proxy in DOWNLOAD_PROXIES) {
            try {
                val url = proxy.format(REPO_OWNER, REPO_NAME, APK_FILE)
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
                android.util.Log.w(TAG, "下载源失败: $proxy", e)
            }
        }

        onComplete(false)
        Toast.makeText(context, "所有下载源都不可用，请手动访问 GitHub 下载", Toast.LENGTH_LONG).show()
    }

    private fun installApk(context: Context) {
        try {
            // DownloadManager 用 setDestinationInExternalPublicDir 保存到此路径
            val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "ZayuHelper_update.apk")
            if (!file.exists()) {
                android.util.Log.w(TAG, "APK 文件不存在: ${file.absolutePath}")
                Toast.makeText(context, "安装失败：文件未找到", Toast.LENGTH_LONG).show()
                return
            }
            val installUri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(installUri, "application/vnd.android.package-archive")
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
        for (proxy in RAW_PROXIES) {
            try {
                val url = proxy.format(REPO_OWNER, REPO_NAME)
                val connection = URL(url).openConnection() as HttpURLConnection
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
                connection.disconnect()
            } catch (e: Exception) {
                android.util.Log.w(TAG, "RAW 源失败: $proxy", e)
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