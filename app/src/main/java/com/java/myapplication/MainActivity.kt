package com.java.myapplication

import android.accessibilityservice.AccessibilityServiceInfo
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.view.View
import android.view.accessibility.AccessibilityManager
import android.widget.Toast

/**
 * 主界面：只负责生命周期、无障碍服务状态展示、权限引导与更新入口。
 *
 * 职责拆分：
 *   - 界面构造        → [MainActivityUi]
 *   - 配置控件读写    → [SettingsForm]
 *   - 配色与控件工厂  → [UiKit]
 */
class MainActivity : Activity(), MainUiCallbacks {

    private lateinit var ui: UiKit
    private lateinit var form: SettingsForm
    private lateinit var view: MainView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ui = UiKit(this)
        form = SettingsForm(ui) { saveConfig() }
        view = MainActivityUi(this, ui, form, this).build(currentVersionName())
        setContentView(view.root)

        // 清理上一次遗留的更新包
        UpdateChecker.cleanUp(this)
        loadConfig()
    }

    override fun onResume() {
        super.onResume()
        updateServiceStatus()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            // 某些 ROM 上 onResume 时 AccessibilityManager 尚未刷新，
            // onWindowFocusChanged 更靠后，确保读到最新状态
            updateServiceStatus()
        }
    }

    // ── MainUiCallbacks ──

    override fun onOpenAccessibilitySettings() {
        startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
    }

    override fun onRequestBatteryOptimization() {
        // minSdk 24，isIgnoringBatteryOptimizations 等 API 必然可用，无需版本判断
        val pm = getSystemService(Context.POWER_SERVICE) as PowerManager
        if (pm.isIgnoringBatteryOptimizations(packageName)) {
            Toast.makeText(this, "已在电池优化白名单中", Toast.LENGTH_SHORT).show()
            return
        }
        try {
            startActivity(
                Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
                    data = Uri.parse("package:$packageName")
                }
            )
        } catch (e: Exception) {
            startActivity(Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS))
        }
    }

    override fun onManageAutoStart() {
        if (!AutoStartHelper.jump(this)) {
            Toast.makeText(this, "请手动在系统设置中开启自启动管理", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCheckUpdate() {
        checkForUpdate()
    }

    override fun onSave() {
        saveConfig()
    }

    // ── 服务状态 ──

    private fun currentVersionName(): String = try {
        packageManager.getPackageInfo(packageName, 0).versionName ?: "?"
    } catch (e: Exception) {
        "?"
    }

    private fun updateServiceStatus() {
        val enabled = isAccessibilityServiceEnabled()
        val color = if (enabled) ui.colorSuccess else ui.colorWarning
        view.tvStatus.text = if (enabled) "服务状态：已开启" else "服务状态：未开启"
        view.tvStatus.setTextColor(color)
        // 圆点颜色原先一直停在初始的红色，这里补上
        view.statusDot.background = ui.circleDrawable(color)
        view.btnOpenSettings.visibility = if (enabled) View.GONE else View.VISIBLE
    }

    private fun isAccessibilityServiceEnabled(): Boolean {
        val am = getSystemService(ACCESSIBILITY_SERVICE) as AccessibilityManager
        val enabledServices =
            am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK)
        val ourService = "${packageName}.QQAccessibilityService"
        return enabledServices.any { it.resolveInfo?.serviceInfo?.name == ourService }
    }

    // ── 更新 ──

    private fun checkForUpdate() {
        view.btnCheckUpdate.isEnabled = false
        view.btnCheckUpdate.text = "检查中..."
        Thread {
            try {
                val result = UpdateChecker.checkUpdate(this)
                runOnUiThread {
                    view.btnCheckUpdate.isEnabled = true
                    view.btnCheckUpdate.text = "检查更新"
                    when {
                        result == null ->
                            Toast.makeText(this, "检查更新失败，请检查网络连接", Toast.LENGTH_SHORT).show()
                        result.hasUpdate -> showUpdateDialog(result.latestVersion)
                        else -> Toast.makeText(this, "当前已是最新版本", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    view.btnCheckUpdate.isEnabled = true
                    view.btnCheckUpdate.text = "检查更新"
                    Toast.makeText(this, "检查更新失败", Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }

    private fun showUpdateDialog(version: String) {
        if (!UpdateChecker.canRequestInstallPackages(this)) {
            AlertDialog.Builder(this)
                .setTitle("需要安装权限")
                .setMessage("请先开启「安装未知应用」权限，否则无法自动安装更新。")
                .setPositiveButton("去设置") { _, _ ->
                    UpdateChecker.openInstallPermissionSettings(this)
                }
                .setNegativeButton("取消", null)
                .show()
            return
        }

        AlertDialog.Builder(this)
            .setTitle("发现新版本 v$version")
            .setMessage("是否下载更新？下载完成后会自动校验安装包签名，校验不通过不会安装。")
            .setPositiveButton("下载") { _, _ ->
                UpdateChecker.downloadUpdate(
                    this,
                    onStart = { Toast.makeText(this, "开始下载...", Toast.LENGTH_SHORT).show() },
                    onComplete = { result ->
                        val message = when (result) {
                            UpdateChecker.DownloadResult.SUCCESS -> null
                            UpdateChecker.DownloadResult.ALREADY_LATEST -> "当前已是最新版本"
                            UpdateChecker.DownloadResult.REJECTED -> "安装包校验未通过，已拒绝安装"
                            UpdateChecker.DownloadResult.FAILED ->
                                "下载失败，请稍后重试或到 GitHub Releases 手动下载"
                        }
                        if (message != null) {
                            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                        }
                    }
                )
            }
            .setNegativeButton("取消", null)
            .show()
    }

    // ── 配置 ──

    private fun loadConfig() {
        form.bind(CatConfig.load(this))
    }

    private fun saveConfig() {
        val config = CatConfig()
        form.readInto(config)
        CatConfig.save(this, config)
    }
}