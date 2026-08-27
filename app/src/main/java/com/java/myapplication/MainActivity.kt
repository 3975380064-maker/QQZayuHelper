package com.java.myapplication

import android.accessibilityservice.AccessibilityServiceInfo
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.view.View
import android.view.accessibility.AccessibilityManager
import android.widget.*

class MainActivity : Activity() {

    private lateinit var cbEnabled: CheckBox
    private lateinit var cbMeow: CheckBox
    private lateinit var cbWoToBenmiao: CheckBox
    private lateinit var cbNiToZhuren: CheckBox
    private lateinit var cbEmoticon: CheckBox
    private lateinit var rbRealtime: RadioButton
    private lateinit var rbPunctuation: RadioButton
    private lateinit var etCustomEmoticons: EditText
    private lateinit var tvStatus: TextView
    private lateinit var btnOpenSettings: Button
    private lateinit var etWoReplacement: EditText
    private lateinit var etNiReplacement: EditText
    private lateinit var etMeowSuffix: EditText
    private lateinit var etIdleDelay: EditText
    private lateinit var btnBatteryOpt: Button
    private lateinit var btnWakeLock: Button
    private lateinit var btnCheckUpdate: Button
    private lateinit var tvVersion: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 版本号
        val versionName = try {
            packageManager.getPackageInfo(packageName, 0).versionName ?: "?"
        } catch (e: Exception) { "?" }

        val scrollView = ScrollView(this)
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
        }

        // ── 标题栏 ──
        val titleRow = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = android.view.Gravity.CENTER_VERTICAL
            setPadding(0, 0, 0, 20)
        }
        titleRow.addView(TextView(this).apply {
            text = "杂鱼助手"
            textSize = 22f
            setTextColor(0xFF222222.toInt())
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        })
        tvVersion = TextView(this).apply {
            text = "v$versionName"
            textSize = 12f
            setTextColor(0xFF999999.toInt())
        }
        titleRow.addView(tvVersion)
        layout.addView(titleRow)

        // ── 服务状态 ──
        tvStatus = TextView(this).apply {
            textSize = 15f
            setPadding(0, 0, 0, 12)
        }
        layout.addView(tvStatus)

        btnOpenSettings = Button(this).apply {
            text = "前往系统设置开启无障碍"
            setOnClickListener { startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)) }
        }
        layout.addView(btnOpenSettings)

        // ── 分隔线 ──
        layout.addView(divider())

        // ── 总开关 ──
        cbEnabled = CheckBox(this).apply {
            text = "启用文字替换功能"
            isChecked = true
            setOnCheckedChangeListener { _, _ -> autoSave() }
        }
        layout.addView(cbEnabled)

        // ── 分隔线 ──
        layout.addView(divider())

        // ── 权限与续航 ──
        layout.addView(sectionTitle("权限与续航"))

        btnBatteryOpt = Button(this).apply {
            text = "申请电池优化白名单"
            setOnClickListener { requestBatteryOptimization() }
        }
        layout.addView(btnBatteryOpt)

        btnWakeLock = Button(this).apply {
            text = "保持唤醒（屏幕关闭不暂停）"
            setOnClickListener { requestWakeLock() }
        }
        layout.addView(btnWakeLock)

        // ── 分隔线 ──
        layout.addView(divider())

        // ── 替换规则 ──
        layout.addView(sectionTitle("替换规则"))

        cbMeow = CheckBox(this).apply {
            text = "句尾加后缀"
            isChecked = true
            setOnCheckedChangeListener { _, _ -> autoSave() }
        }
        layout.addView(cbMeow)

        layout.addView(labelText("句尾后缀（默认：喵）"))
        etMeowSuffix = EditText(this).apply {
            hint = "喵、唔喵、咩..."
            setPadding(32, 8, 32, 8)
            textSize = 14f
        }
        layout.addView(etMeowSuffix)

        layout.addView(spacer(4))

        cbWoToBenmiao = CheckBox(this).apply {
            text = "我 →"
            setOnCheckedChangeListener { _, _ -> autoSave() }
        }
        layout.addView(cbWoToBenmiao)

        etWoReplacement = EditText(this).apply {
            hint = "本喵、咱、吾辈、人家..."
            setPadding(48, 8, 32, 8)
            textSize = 14f
        }
        layout.addView(etWoReplacement)

        layout.addView(spacer(4))

        cbNiToZhuren = CheckBox(this).apply {
            text = "你 →"
            setOnCheckedChangeListener { _, _ -> autoSave() }
        }
        layout.addView(cbNiToZhuren)

        etNiReplacement = EditText(this).apply {
            hint = "主人、杂鱼、笨蛋主人..."
            setPadding(48, 8, 32, 8)
            textSize = 14f
        }
        layout.addView(etNiReplacement)

        layout.addView(spacer(4))

        cbEmoticon = CheckBox(this).apply {
            text = "随机添加后缀表情"
            setOnCheckedChangeListener { _, _ -> autoSave() }
        }
        layout.addView(cbEmoticon)

        // ── 分隔线 ──
        layout.addView(divider())

        // ── 处理模式 ──
        layout.addView(sectionTitle("处理模式"))

        val radioGroup = RadioGroup(this).apply {
            orientation = LinearLayout.VERTICAL
            setOnCheckedChangeListener { _, _ -> autoSave() }
        }
        rbRealtime = RadioButton(this).apply { text = "智能模式"; id = 1 }
        rbPunctuation = RadioButton(this).apply { text = "标点模式"; id = 2 }
        radioGroup.addView(rbRealtime)
        radioGroup.addView(rbPunctuation)
        layout.addView(radioGroup)

        layout.addView(labelText("空闲延迟（秒，智能模式）："))
        etIdleDelay = EditText(this).apply {
            hint = "1"
            textSize = 14f
            inputType = android.text.InputType.TYPE_CLASS_NUMBER
            setPadding(32, 8, 32, 8)
        }
        layout.addView(etIdleDelay)

        // ── 分隔线 ──
        layout.addView(divider())

        // ── 自定义表情 ──
        layout.addView(sectionTitle("自定义表情"))

        etCustomEmoticons = EditText(this).apply {
            hint = "每行一个，留空使用默认"
            setLines(4); setMinLines(4); textSize = 14f
            setPadding(0, 8, 0, 8)
        }
        layout.addView(etCustomEmoticons)

        // ── 分隔线 ──
        layout.addView(divider())

        // ── 更新 ──
        layout.addView(sectionTitle("更新"))

        btnCheckUpdate = Button(this).apply {
            text = "检查更新"
            setOnClickListener { checkForUpdate() }
        }
        layout.addView(btnCheckUpdate)

        // ── 分隔线 ──
        layout.addView(divider())

        // ── 保存按钮 ──
        layout.addView(Button(this).apply {
            text = "保存设置"
            setOnClickListener { saveConfig() }
        })

        layout.addView(TextView(this).apply {
            text = "修改勾选后自动保存，无需手动保存"
            textSize = 12f
            setTextColor(0xFF999999.toInt())
            setPadding(0, 8, 0, 8)
        })

        // ── 底部信息 ──
        layout.addView(spacer(8))

        layout.addView(TextView(this).apply {
            text = "作者：喵喵喵"
            textSize = 13f
            setTextColor(0xFF666666.toInt())
            gravity = android.view.Gravity.CENTER_HORIZONTAL
            setPadding(0, 8, 0, 4)
        })

        val tvGithub = TextView(this)
        tvGithub.text = "https://github.com/3975380064-maker/QQZayuHelper"
        tvGithub.textSize = 12f
        tvGithub.setTextColor(0xFF1976D2.toInt())
        tvGithub.gravity = android.view.Gravity.CENTER_HORIZONTAL
        tvGithub.setPadding(0, 0, 0, 32)
        tvGithub.setOnClickListener {
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/3975380064-maker/QQZayuHelper")))
            } catch (e: Exception) {
                Toast.makeText(this, "无法打开浏览器", Toast.LENGTH_SHORT).show()
            }
        }
        layout.addView(tvGithub)

        scrollView.addView(layout)
        setContentView(scrollView)

        loadConfig()
    }

    override fun onResume() {
        super.onResume()
        updateServiceStatus()
    }

    // ── 辅助视图构建 ──

    private fun divider(): View = View(this).apply {
        setBackgroundColor(0xFFDDDDDD.toInt())
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, 1
        )
        lp.setMargins(0, 16, 0, 16)
        layoutParams = lp
    }

    private fun sectionTitle(text: String): TextView = TextView(this).apply {
        this.text = text
        textSize = 14f
        setTextColor(0xFF888888.toInt())
        setPadding(0, 0, 0, 12)
    }

    private fun labelText(text: String): TextView = TextView(this).apply {
        this.text = text
        textSize = 12f
        setTextColor(0xFF999999.toInt())
        setPadding(32, 4, 0, 4)
    }

    private fun spacer(heightDp: Int): TextView = TextView(this).apply {
        text = " "
        textSize = heightDp.toFloat()
    }

    // ── 业务逻辑 ──

    private fun updateServiceStatus() {
        val enabled = isAccessibilityServiceEnabled()
        tvStatus.text = if (enabled) "服务状态：已开启" else "服务状态：未开启"
        tvStatus.setTextColor(if (enabled) 0xFF4CAF50.toInt() else 0xFFFF5722.toInt())
        btnOpenSettings.visibility = if (enabled) View.GONE else View.VISIBLE
    }

    private fun isAccessibilityServiceEnabled(): Boolean {
        val am = getSystemService(ACCESSIBILITY_SERVICE) as AccessibilityManager
        val enabledServices = am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK)
        val ourService = "${packageName}.QQAccessibilityService"
        return enabledServices.any { it.resolveInfo.serviceInfo.name == ourService }
    }

    private fun requestBatteryOptimization() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val pm = getSystemService(Context.POWER_SERVICE) as PowerManager
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                try {
                    val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
                        data = Uri.parse("package:$packageName")
                    }
                    startActivity(intent)
                } catch (e: Exception) {
                    startActivity(Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS))
                }
            } else {
                Toast.makeText(this, "已在电池优化白名单中", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Android 6.0 以上才支持", Toast.LENGTH_SHORT).show()
        }
    }

    private fun requestWakeLock() {
        AlertDialog.Builder(this)
            .setTitle("保持唤醒")
            .setMessage("WAKE_LOCK 权限已声明，安装时自动授权。\n无障碍服务运行期间会持有唤醒锁，防止屏幕关闭后 CPU 休眠导致功能暂停。")
            .setPositiveButton("知道了", null)
            .show()
    }

    private fun checkForUpdate() {
        btnCheckUpdate.isEnabled = false
        btnCheckUpdate.text = "检查中..."
        Thread {
            try {
                val result = UpdateChecker.checkUpdate(this)
                runOnUiThread {
                    btnCheckUpdate.isEnabled = true
                    btnCheckUpdate.text = "检查更新"
                    if (result == null) {
                        Toast.makeText(this, "检查更新失败，请检查网络连接", Toast.LENGTH_SHORT).show()
                    } else if (result.first) {
                        showUpdateDialog(result.second)
                    } else {
                        Toast.makeText(this, "当前已是最新版本", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    btnCheckUpdate.isEnabled = true
                    btnCheckUpdate.text = "检查更新"
                    Toast.makeText(this, "检查更新失败", Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }

    private fun showUpdateDialog(version: String) {
        AlertDialog.Builder(this)
            .setTitle("发现新版本 v$version")
            .setMessage("是否下载更新？")
            .setPositiveButton("下载") { _, _ ->
                UpdateChecker.downloadUpdate(this,
                    onStart = { Toast.makeText(this, "开始下载...", Toast.LENGTH_SHORT).show() },
                    onComplete = { success ->
                        if (!success) Toast.makeText(this, "下载失败，请手动访问 GitHub", Toast.LENGTH_LONG).show()
                    }
                )
            }
            .setNegativeButton("取消", null)
            .show()
    }

    private fun autoSave() { saveConfig() }

    private fun loadConfig() {
        val config = CatConfig.load(this)
        cbEnabled.isChecked = config.enabled
        cbMeow.isChecked = config.enableMeow
        cbWoToBenmiao.isChecked = config.enableWoToBenmiao
        cbNiToZhuren.isChecked = config.enableNiToZhuren
        cbEmoticon.isChecked = config.enableRandomEmoticon
        rbRealtime.isChecked = config.processingMode == CatConfig.REAL_TIME_MODE
        rbPunctuation.isChecked = config.processingMode != CatConfig.REAL_TIME_MODE
        etCustomEmoticons.setText(config.customEmoticons.joinToString("\n"))
        etWoReplacement.setText(config.woReplacement)
        etNiReplacement.setText(config.niReplacement)
        etMeowSuffix.setText(config.meowSuffix)
        etIdleDelay.setText(config.idleDelayMs.toString())
    }

    private fun saveConfig() {
        val config = CatConfig()
        config.enabled = cbEnabled.isChecked
        config.enableMeow = cbMeow.isChecked
        config.enableWoToBenmiao = cbWoToBenmiao.isChecked
        config.enableNiToZhuren = cbNiToZhuren.isChecked
        config.enableRandomEmoticon = cbEmoticon.isChecked
        config.processingMode = if (rbRealtime.isChecked) CatConfig.REAL_TIME_MODE else CatConfig.PUNCTUATION_MODE
        config.customEmoticons = etCustomEmoticons.text.toString().split("\n").filter { it.isNotBlank() }.toTypedArray()

        val wo = etWoReplacement.text.toString().trim()
        if (wo.isNotEmpty()) config.woReplacement = wo

        val ni = etNiReplacement.text.toString().trim()
        if (ni.isNotEmpty()) config.niReplacement = ni

        val meow = etMeowSuffix.text.toString().trim()
        if (meow.isNotEmpty()) config.meowSuffix = meow

        val delay = etIdleDelay.text.toString().trim()
        if (delay.isNotEmpty()) {
            val d = delay.toIntOrNull()
            if (d != null && d > 0) config.idleDelayMs = d
        }

        CatConfig.save(this, config)
    }
}