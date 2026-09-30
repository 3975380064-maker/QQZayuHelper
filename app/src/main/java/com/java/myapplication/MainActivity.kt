package com.java.myapplication

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.java.myapplication.databinding.ActivityMainBinding

/**
 * 主界面：生命周期、服务状态展示、权限引导、更新入口。
 *
 * 界面全部在 res/layout 里，这里只做绑定与业务；不再用代码 new 控件。
 * 用 AppCompatActivity 是因为 DayNight 主题切换依赖 AppCompat 的代理，
 * 普通 Activity 在部分 ROM 上不会跟随系统切深浅色。
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var form: SettingsForm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 让内容延伸到状态栏/导航栏下面，再由下面的 inset 监听统一留白
        WindowCompat.setDecorFitsSystemWindows(window, false)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applyWindowInsets()

        form = SettingsForm(binding, layoutInflater) { saveConfig() }
        binding.sectionAbout.tvVersion.text =
            getString(R.string.about_version, currentVersionName())

        setupListeners()
        UpdateChecker.cleanUp(this)
        loadConfig()
    }

    override fun onResume() {
        super.onResume()
        updateServiceStatus()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        // 某些 ROM 上 onResume 时 AccessibilityManager 尚未刷新，
        // onWindowFocusChanged 更靠后，确保读到最新状态
        if (hasFocus) updateServiceStatus()
    }

    /**
     * targetSdk 35 起系统强制边到边，状态栏/导航栏会盖在内容上。
     * 这里把系统栏高度作为 padding 加给根布局，深浅色下都不会被遮挡。
     */
    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            val bars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
            )
            // 顶栏自己吸收状态栏内边距：它的背景能一直铺到屏幕顶端，
            // 否则状态栏区域会露出页面底色、和顶栏之间出现一条色带
            binding.appBar.updatePadding(left = bars.left, top = bars.top, right = bars.right)
            binding.scroll.updatePadding(
                left = bars.left,
                right = bars.right,
                bottom = bars.bottom
            )
            WindowInsetsCompat.CONSUMED
        }
    }

    private fun setupListeners() {
        binding.sectionStatus.btnOpenAccessibility.setOnClickListener {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }
        binding.sectionAbout.btnCheckUpdate.setOnClickListener { checkForUpdate() }
        binding.sectionAbout.btnBatteryOptimization.setOnClickListener { requestBatteryOptimization() }
        binding.sectionAbout.btnAutostart.setOnClickListener { requestAutoStart() }
        binding.sectionAbout.rowGithub.setOnClickListener { openProjectPage() }

        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_save -> {
                    saveConfig()
                    Snackbar.make(binding.root, R.string.snackbar_saved, Snackbar.LENGTH_SHORT).show()
                    true
                }
                R.id.action_github -> {
                    openProjectPage()
                    true
                }
                else -> false
            }
        }
    }

    // ── 服务状态 ──

    private fun currentVersionName(): String = try {
        packageManager.getPackageInfo(packageName, 0).versionName ?: "?"
    } catch (e: Exception) {
        "?"
    }

    private fun updateServiceStatus() {
        val enabled = isAccessibilityServiceEnabled()
        // 语义色有日/夜两套，取出来的就是当前模式下的正确颜色
        val color = ContextCompat.getColor(
            this,
            if (enabled) R.color.status_on else R.color.status_off
        )
        binding.sectionStatus.statusDot.backgroundTintList = ColorStateList.valueOf(color)
        binding.sectionStatus.tvStatus.setText(if (enabled) R.string.status_on else R.string.status_off)
        binding.sectionStatus.tvStatus.setTextColor(color)
        binding.sectionStatus.tvStatusHint.setText(
            if (enabled) R.string.status_hint_on else R.string.status_hint_off
        )
        binding.sectionStatus.btnOpenAccessibility.visibility =
            if (enabled) View.GONE else View.VISIBLE
    }

    private fun isAccessibilityServiceEnabled(): Boolean {
        val am = getSystemService(ACCESSIBILITY_SERVICE) as android.view.accessibility.AccessibilityManager
        val enabledServices =
            am.getEnabledAccessibilityServiceList(android.accessibilityservice.AccessibilityServiceInfo.FEEDBACK_ALL_MASK)
        val ourService = "${packageName}.QQAccessibilityService"
        return enabledServices.any { it.resolveInfo?.serviceInfo?.name == ourService }
    }

    // ── 权限引导 ──

    private fun requestBatteryOptimization() {
        // minSdk 24，isIgnoringBatteryOptimizations 等 API 必然可用，无需版本判断
        val pm = getSystemService(Context.POWER_SERVICE) as PowerManager
        if (pm.isIgnoringBatteryOptimizations(packageName)) {
            toast(R.string.toast_battery_whitelisted)
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

    private fun requestAutoStart() {
        if (!AutoStartHelper.jump(this)) {
            toast(R.string.toast_autostart_manual)
        }
    }

    private fun openProjectPage() {
        try {
            startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/3975380064-maker/QQZayuHelper"))
            )
        } catch (e: Exception) {
            toast(R.string.toast_cannot_open_browser)
        }
    }

    // ── 更新 ──

    private fun checkForUpdate() {
        val button = binding.sectionAbout.btnCheckUpdate
        button.isEnabled = false
        button.setText(R.string.toast_checking)
        Thread {
            try {
                val result = UpdateChecker.checkUpdate(this)
                runOnUiThread {
                    button.isEnabled = true
                    button.setText(R.string.action_check_update)
                    when {
                        result == null -> toast(R.string.toast_check_failed)
                        result.hasUpdate -> showUpdateDialog(result.latestVersion)
                        else -> toast(R.string.toast_up_to_date)
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    button.isEnabled = true
                    button.setText(R.string.action_check_update)
                    toast(R.string.toast_update_failed)
                }
            }
        }.start()
    }

    private fun showUpdateDialog(version: String) {
        if (!UpdateChecker.canRequestInstallPackages(this)) {
            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.dialog_install_title)
                .setMessage(R.string.dialog_install_message)
                .setPositiveButton(R.string.dialog_go_settings) { _, _ ->
                    UpdateChecker.openInstallPermissionSettings(this)
                }
                .setNegativeButton(R.string.dialog_cancel, null)
                .show()
            return
        }

        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.dialog_update_title, version))
            .setMessage(R.string.dialog_update_message)
            .setPositiveButton(R.string.dialog_download) { _, _ ->
                UpdateChecker.downloadUpdate(
                    this,
                    onStart = { toast(R.string.toast_download_start) },
                    onComplete = { result ->
                        val message = when (result) {
                            UpdateChecker.DownloadResult.SUCCESS -> null
                            UpdateChecker.DownloadResult.ALREADY_LATEST -> R.string.toast_up_to_date
                            UpdateChecker.DownloadResult.REJECTED -> R.string.toast_verify_rejected
                            UpdateChecker.DownloadResult.FAILED -> R.string.toast_download_failed
                        }
                        if (message != null) toast(message)
                    }
                )
            }
            .setNegativeButton(R.string.dialog_cancel, null)
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

    private fun toast(messageRes: Int) {
        Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show()
    }
}