package com.java.myapplication

import android.accessibilityservice.AccessibilityServiceInfo
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.view.accessibility.AccessibilityManager
import android.widget.*
import androidx.cardview.widget.CardView
import com.google.android.material.button.MaterialButton
import com.google.android.material.radiobutton.MaterialRadioButton
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : Activity() {

    private lateinit var switchEnabled: SwitchMaterial
    private lateinit var switchMeow: SwitchMaterial
    private lateinit var switchWoToBenmiao: SwitchMaterial
    private lateinit var switchNiToZhuren: SwitchMaterial
    private lateinit var switchEmoticon: SwitchMaterial
    private lateinit var rbRealtime: MaterialRadioButton
    private lateinit var rbPunctuation: MaterialRadioButton
    private lateinit var etCustomEmoticons: TextInputEditText
    private lateinit var etCustomRules: TextInputEditText
    private lateinit var tvStatus: TextView
    private lateinit var btnOpenSettings: MaterialButton
    private lateinit var etWoReplacement: TextInputEditText
    private lateinit var etNiReplacement: TextInputEditText
    private lateinit var etMeowSuffix: TextInputEditText
    private lateinit var etIdleDelay: TextInputEditText
    private lateinit var btnBatteryOpt: MaterialButton
    private lateinit var btnAutoStart: MaterialButton
    private lateinit var btnCheckUpdate: MaterialButton
    private lateinit var tvVersion: TextView

    // 颜色常量
    private val colorBackground = 0xFFF5F5F7.toInt()
    private val colorCard = 0xFFFFFFFF.toInt()
    private val colorPrimary = 0xFF007AFF.toInt()
    private val colorTextPrimary = 0xFF1C1C1E.toInt()
    private val colorTextSecondary = 0xFF8E8E93.toInt()
    private val colorDivider = 0xFFE5E5EA.toInt()
    private val colorSuccess = 0xFF34C759.toInt()
    private val colorWarning = 0xFFFF3B30.toInt()

    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val versionName = try {
            packageManager.getPackageInfo(packageName, 0).versionName ?: "?"
        } catch (e: Exception) { "?" }

        val scrollView = ScrollView(this).apply {
            setBackgroundColor(colorBackground)
        }

        val rootLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 24, 24, 48)
        }

        // ── 顶部标题区 ──
        val header = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(8, 16, 8, 24)
        }
        val tvTitle = TextView(this).apply {
            text = "杂鱼助手"
            textSize = 28f
            setTextColor(colorTextPrimary)
            setTypeface(typeface, android.graphics.Typeface.BOLD)
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        }
        tvVersion = TextView(this).apply {
            text = "v$versionName"
            textSize = 14f
            setTextColor(colorTextSecondary)
            setPadding(8, 4, 8, 4)
            background = GradientDrawable().apply {
                cornerRadius = 12f
                setColor(0xFFE5E5EA.toInt())
            }
        }
        header.addView(tvTitle)
        header.addView(tvVersion)
        rootLayout.addView(header)

        // ── 状态卡片 ──
        val statusCard = createCard()
        val statusLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 20, 24, 20)
        }
        val statusRow = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }
        val statusDot = View(this).apply {
            layoutParams = LinearLayout.LayoutParams(10, 10).apply {
                setMargins(0, 0, 12, 0)
            }
            background = GradientDrawable().apply {
                shape = GradientDrawable.OVAL
                setColor(colorWarning)
            }
        }
        tvStatus = TextView(this).apply {
            text = "服务状态：未开启"
            textSize = 15f
            setTextColor(colorTextPrimary)
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        }
        statusRow.addView(statusDot)
        statusRow.addView(tvStatus)

        btnOpenSettings = createOutlineButton("前往系统设置开启无障碍") {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }
        btnOpenSettings.visibility = View.GONE

        statusLayout.addView(statusRow)
        statusLayout.addView(spacerView(12))
        statusLayout.addView(btnOpenSettings)
        statusCard.addView(statusLayout)
        rootLayout.addView(statusCard)

        // ── 主开关卡片 ──
        val mainSwitchCard = createCard()
        val mainSwitchLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 16, 24, 16)
        }
        switchEnabled = createSwitch("启用文字替换功能", true)
        mainSwitchLayout.addView(switchEnabled)
        mainSwitchCard.addView(mainSwitchLayout)
        rootLayout.addView(mainSwitchCard)

        // ── 权限与续航 ──
        rootLayout.addView(createSectionTitle("权限与续航"))
        val batteryCard = createCard()
        val batteryLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(8, 8, 8, 8)
        }
        btnBatteryOpt = createTextButton("申请电池优化白名单") { requestBatteryOptimization() }
        btnAutoStart = createTextButton("管理自启动") { requestAutoStart() }
        batteryLayout.addView(btnBatteryOpt)
        batteryLayout.addView(createThinDivider())
        batteryLayout.addView(btnAutoStart)
        batteryCard.addView(batteryLayout)
        rootLayout.addView(batteryCard)

        // ── 替换规则 ──
        rootLayout.addView(createSectionTitle("替换规则"))
        val replaceCard = createCard()
        val replaceLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(8, 8, 8, 8)
        }

        switchMeow = createSwitch("句尾添加后缀", true)
        val (meowEdit, meowLayout) = createTextInputRow("句尾后缀", "喵、唔喵、咩...")
        etMeowSuffix = meowEdit

        switchWoToBenmiao = createSwitch("替换「我」", true)
        val (woEdit, woLayout) = createTextInputRow("替换为", "本喵、咱、吾辈、人家...")
        etWoReplacement = woEdit

        switchNiToZhuren = createSwitch("替换「你」", true)
        val (niEdit, niLayout) = createTextInputRow("替换为", "主人、杂鱼、笨蛋主人...")
        etNiReplacement = niEdit

        switchEmoticon = createSwitch("随机添加后缀表情", true)

        replaceLayout.addView(switchMeow)
        replaceLayout.addView(meowLayout)
        replaceLayout.addView(createThinDivider())
        replaceLayout.addView(switchWoToBenmiao)
        replaceLayout.addView(woLayout)
        replaceLayout.addView(createThinDivider())
        replaceLayout.addView(switchNiToZhuren)
        replaceLayout.addView(niLayout)
        replaceLayout.addView(createThinDivider())
        replaceLayout.addView(switchEmoticon)
        replaceCard.addView(replaceLayout)
        rootLayout.addView(replaceCard)

        // ── 处理模式 ──
        rootLayout.addView(createSectionTitle("处理模式"))
        val modeCard = createCard()
        val modeLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 16, 24, 16)
        }
        val radioGroup = RadioGroup(this).apply {
            orientation = LinearLayout.VERTICAL
            setOnCheckedChangeListener { _, _ ->
                if (!isLoading) autoSave()
            }
        }
        rbRealtime = MaterialRadioButton(this).apply {
            text = "智能模式"
            textSize = 15f
            setTextColor(colorTextPrimary)
            id = 1
        }
        rbPunctuation = MaterialRadioButton(this).apply {
            text = "标点模式"
            textSize = 15f
            setTextColor(colorTextPrimary)
            id = 2
        }
        radioGroup.addView(rbRealtime)
        radioGroup.addView(rbPunctuation)

        val (delayEdit, delayLayout) = createTextInputRow("空闲延迟（毫秒，仅智能模式）", "1000")
        etIdleDelay = delayEdit
        etIdleDelay.inputType = android.text.InputType.TYPE_CLASS_NUMBER

        modeLayout.addView(radioGroup)
        modeLayout.addView(spacerView(12))
        modeLayout.addView(delayLayout)
        modeCard.addView(modeLayout)
        rootLayout.addView(modeCard)

        // ── 自定义表情 ──
        rootLayout.addView(createSectionTitle("自定义表情"))
        val emoticonCard = createCard()
        val emoticonLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 16, 24, 16)
        }
        val emoticonLabel = TextView(this).apply {
            text = "每行一个，留空则使用默认表情"
            textSize = 13f
            setTextColor(colorTextSecondary)
            setPadding(0, 0, 0, 8)
        }
        etCustomEmoticons = TextInputEditText(this).apply {
            setLines(4)
            minLines = 4
            textSize = 15f
            setTextColor(colorTextPrimary)
            background = null
            gravity = Gravity.TOP
            setPadding(0, 8, 0, 8)
        }
        emoticonLayout.addView(emoticonLabel)
        emoticonLayout.addView(etCustomEmoticons, LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ))
        emoticonCard.addView(emoticonLayout)
        rootLayout.addView(emoticonCard)

        // ── 自定义替换规则 ──
        rootLayout.addView(createSectionTitle("自定义替换规则"))
        val rulesCard = createCard()
        val rulesLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 16, 24, 16)
        }
        val rulesLabel = TextView(this).apply {
            text = "每行一条，格式：原词=替换词\n例如：说=曰、吗=嘛"
            textSize = 13f
            setTextColor(colorTextSecondary)
            setPadding(0, 0, 0, 8)
        }
        val etCustomRules = TextInputEditText(this).apply {
            setLines(3)
            minLines = 3
            textSize = 15f
            setTextColor(colorTextPrimary)
            background = null
            gravity = Gravity.TOP
            setPadding(0, 8, 0, 8)
        }
        // 存为成员变量供 loadConfig/saveConfig 使用
        this.etCustomRules = etCustomRules
        rulesLayout.addView(rulesLabel)
        rulesLayout.addView(etCustomRules, LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ))
        rulesCard.addView(rulesLayout)
        rootLayout.addView(rulesCard)

        // ── 更新 ──
        rootLayout.addView(createSectionTitle("更新"))
        val updateCard = createCard()
        val updateLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(8, 8, 8, 8)
        }
        btnCheckUpdate = createTextButton("检查更新") { checkForUpdate() }
        updateLayout.addView(btnCheckUpdate)
        updateCard.addView(updateLayout)
        rootLayout.addView(updateCard)

        // ── 保存按钮 ──
        val saveBtn = createFilledButton("保存设置") { saveConfig() }
        val saveHint = TextView(this).apply {
            text = "修改后自动保存，也可手动点击保存"
            textSize = 12f
            setTextColor(colorTextSecondary)
            gravity = Gravity.CENTER
            setPadding(0, 8, 0, 0)
        }
        rootLayout.addView(spacerView(16))
        rootLayout.addView(saveBtn)
        rootLayout.addView(saveHint)

        // ── 底部 ──
        rootLayout.addView(spacerView(32))
        val tvAuthor = TextView(this).apply {
            text = "作者：喵喵喵"
            textSize = 13f
            setTextColor(colorTextSecondary)
            gravity = Gravity.CENTER
        }
        val tvGithub = TextView(this).apply {
            text = "github.com/3975380064-maker/QQZayuHelper"
            textSize = 13f
            setTextColor(colorPrimary)
            gravity = Gravity.CENTER
            setPadding(0, 8, 0, 0)
            setOnClickListener {
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/3975380064-maker/QQZayuHelper")))
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, "无法打开浏览器", Toast.LENGTH_SHORT).show()
                }
            }
        }
        rootLayout.addView(tvAuthor)
        rootLayout.addView(tvGithub)

        scrollView.addView(rootLayout)
        setContentView(scrollView)

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

    // ── 现代化 UI 构建辅助 ──

    private fun createCard(): CardView {
        val card = CardView(this)
        card.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(0, 0, 0, 16)
        }
        card.radius = 16f
        card.cardElevation = 0f
        card.setCardBackgroundColor(colorCard)
        return card
    }

    private fun createSectionTitle(text: String): TextView = TextView(this).apply {
        this.text = text
        textSize = 13f
        setTextColor(colorTextSecondary)
        setTypeface(typeface, android.graphics.Typeface.BOLD)
        setPadding(12, 16, 0, 8)
    }

    private fun createSwitch(text: String, checked: Boolean): SwitchMaterial = SwitchMaterial(this).apply {
        this.text = text
        isChecked = checked
        textSize = 16f
        setTextColor(colorTextPrimary)
        setPadding(16, 16, 16, 16)
        setOnCheckedChangeListener { _, _ ->
            if (!isLoading) autoSave()
        }
    }

    /**
     * 创建带浮动标签的文本输入行，返回 (EditText, TextInputLayout) 对。
     * 调用方把 TextInputLayout 添加到父布局，EditText 通过返回的引用访问值。
     */
    private fun createTextInputRow(hint: String, helper: String): Pair<TextInputEditText, TextInputLayout> {
        val edit = TextInputEditText(this).apply {
            textSize = 15f
            setTextColor(colorTextPrimary)
            setPadding(0, 8, 0, 8)
        }
        val layout = TextInputLayout(this).apply {
            this.hint = hint
            setHelperText(helper)
            setHelperTextColor(android.content.res.ColorStateList.valueOf(colorTextSecondary))
            boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_NONE
            setPadding(16, 4, 16, 8)
            addView(edit)
        }
        return Pair(edit, layout)
    }

    private fun createOutlineButton(text: String, onClick: () -> Unit): MaterialButton {
        val btn = MaterialButton(this, null, 0)
        btn.text = text
        btn.textSize = 16f
        btn.setTextColor(colorPrimary)
        btn.strokeColor = android.content.res.ColorStateList.valueOf(colorPrimary)
        btn.strokeWidth = 2
        btn.cornerRadius = 24
        btn.setBackgroundColor(0x00000000)
        btn.elevation = 0f
        btn.stateListAnimator = null
        btn.gravity = Gravity.CENTER
        btn.setOnClickListener { onClick() }
        btn.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        return btn
    }

    private fun createFilledButton(text: String, onClick: () -> Unit): MaterialButton {
        val btn = MaterialButton(this, null, 0)
        btn.text = text
        btn.textSize = 16f
        btn.setTextColor(0xFFFFFFFF.toInt())
        btn.cornerRadius = 24
        btn.setBackgroundColor(colorPrimary)
        btn.elevation = 0f
        btn.stateListAnimator = null
        btn.gravity = Gravity.CENTER
        btn.setOnClickListener { onClick() }
        btn.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        return btn
    }

    private fun createTextButton(text: String, onClick: () -> Unit): MaterialButton {
        val btn = MaterialButton(this, null, 0)
        btn.text = text
        btn.textSize = 15f
        btn.setTextColor(colorTextPrimary)
        btn.setBackgroundColor(0x00000000)
        btn.elevation = 0f
        btn.stateListAnimator = null
        btn.setOnClickListener { onClick() }
        btn.gravity = Gravity.START or Gravity.CENTER_VERTICAL
        btn.setPadding(32, 28, 32, 28)
        btn.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        btn.rippleColor = android.content.res.ColorStateList.valueOf(0x1F000000)
        return btn
    }

    private fun createThinDivider(): View = View(this).apply {
        setBackgroundColor(colorDivider)
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, 1
        ).apply {
            setMargins(32, 0, 0, 0)
        }
    }

    private fun spacerView(heightDp: Int): View = View(this).apply {
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, heightDp
        )
    }

    // ── 业务逻辑 ──

    private fun updateServiceStatus() {
        val enabled = isAccessibilityServiceEnabled()
        tvStatus.text = if (enabled) "服务状态：已开启" else "服务状态：未开启"
        tvStatus.setTextColor(if (enabled) colorSuccess else colorWarning)
        btnOpenSettings.visibility = if (enabled) View.GONE else View.VISIBLE
    }

    private fun isAccessibilityServiceEnabled(): Boolean {
        val am = getSystemService(ACCESSIBILITY_SERVICE) as AccessibilityManager
        val enabledServices = am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK)
        val ourService = "${packageName}.QQAccessibilityService"
        return enabledServices.any { it.resolveInfo?.serviceInfo?.name == ourService }
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

    private fun requestAutoStart() {
        if (!AutoStartHelper.jump(this)) {
            Toast.makeText(this, "请手动在系统设置中开启自启动管理", Toast.LENGTH_LONG).show()
        }
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
        isLoading = true
        val config = CatConfig.load(this)
        switchEnabled.isChecked = config.enabled
        switchMeow.isChecked = config.enableMeow
        switchWoToBenmiao.isChecked = config.enableWoToBenmiao
        switchNiToZhuren.isChecked = config.enableNiToZhuren
        switchEmoticon.isChecked = config.enableRandomEmoticon
        rbRealtime.isChecked = config.processingMode == CatConfig.REAL_TIME_MODE
        rbPunctuation.isChecked = config.processingMode != CatConfig.REAL_TIME_MODE
        etCustomEmoticons.setText(config.customEmoticons.joinToString("\n"))
        etCustomRules.setText(config.customRules.joinToString("\n"))
        etWoReplacement.setText(config.woReplacement)
        etNiReplacement.setText(config.niReplacement)
        etMeowSuffix.setText(config.meowSuffix)
        // idleDelayMs 存的是毫秒，UI 直接显示
        etIdleDelay.setText(config.idleDelayMs.toString())
        isLoading = false
    }

    private fun saveConfig() {
        val config = CatConfig()
        config.enabled = switchEnabled.isChecked
        config.enableMeow = switchMeow.isChecked
        config.enableWoToBenmiao = switchWoToBenmiao.isChecked
        config.enableNiToZhuren = switchNiToZhuren.isChecked
        config.enableRandomEmoticon = switchEmoticon.isChecked
        config.processingMode = if (rbRealtime.isChecked) CatConfig.REAL_TIME_MODE else CatConfig.PUNCTUATION_MODE
        config.customEmoticons = etCustomEmoticons.text.toString().split("\n").filter { it.isNotBlank() }.toTypedArray()
        config.customRules = etCustomRules.text.toString().split("\n").filter { it.isNotBlank() }.toTypedArray()

        val wo = etWoReplacement.text.toString().trim()
        if (wo.isNotEmpty()) config.woReplacement = wo

        val ni = etNiReplacement.text.toString().trim()
        if (ni.isNotEmpty()) config.niReplacement = ni

        val meow = etMeowSuffix.text.toString().trim()
        if (meow.isNotEmpty()) config.meowSuffix = meow

        val delay = etIdleDelay.text.toString().trim()
        if (delay.isNotEmpty()) {
            val d = delay.toIntOrNull()
            if (d != null && d > 0) config.idleDelayMs = d  // 用户输入毫秒，直接存
        }

        CatConfig.save(this, config)
    }
}