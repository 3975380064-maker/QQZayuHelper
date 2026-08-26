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
    // 自定义替换
    private lateinit var etWoReplacement: EditText
    private lateinit var etNiReplacement: EditText
    private lateinit var etMeowSuffix: EditText
    private lateinit var etIdleDelay: EditText
    // 权限按钮
    private lateinit var btnBatteryOpt: Button
    private lateinit var btnWakeLock: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val scrollView = ScrollView(this)
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
        }

        // 标题
        layout.addView(TextView(this).apply {
            text = "QQ杂鱼助手"
            textSize = 24f
            setTextColor(0xFF333333.toInt())
            setPadding(0, 0, 0, 24)
        })

        // 服务状态
        tvStatus = TextView(this).apply {
            textSize = 16f
            setPadding(0, 0, 0, 16)
        }
        layout.addView(tvStatus)

        btnOpenSettings = Button(this).apply {
            text = "前往系统设置开启无障碍"
            setOnClickListener {
                startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
            }
        }
        layout.addView(btnOpenSettings)

        // ───── 总开关 ─────
        layout.addView(TextView(this).apply {
            text = "───── 总开关 ─────"
            textSize = 14f
            setTextColor(0xFF999999.toInt())
            setPadding(0, 32, 0, 12)
        })

        cbEnabled = CheckBox(this).apply {
            text = "启用文字替换功能"
            isChecked = true
            setPadding(0, 0, 0, 8)
            setOnCheckedChangeListener { _, _ -> autoSave() }
        }
        layout.addView(cbEnabled)

        // ───── 权限申请 ─────
        layout.addView(TextView(this).apply {
            text = "───── 权限与续航 ─────"
            textSize = 14f
            setTextColor(0xFF999999.toInt())
            setPadding(0, 32, 0, 12)
        })

        btnBatteryOpt = Button(this).apply {
            text = "申请电池优化白名单"
            setPadding(0, 8, 0, 8)
            setOnClickListener { requestBatteryOptimization() }
        }
        layout.addView(btnBatteryOpt)

        layout.addView(TextView(this).apply {
            text = " " // 间距
            textSize = 4f
        })

        btnWakeLock = Button(this).apply {
            text = "保持唤醒（屏幕关闭不暂停）"
            setPadding(0, 8, 0, 8)
            setOnClickListener { requestWakeLock() }
        }
        layout.addView(btnWakeLock)

        // ───── 替换规则 ─────
        layout.addView(TextView(this).apply {
            text = "───── 替换规则 ─────"
            textSize = 14f
            setTextColor(0xFF999999.toInt())
            setPadding(0, 32, 0, 12)
        })

        cbMeow = CheckBox(this).apply {
            text = "句尾加后缀"
            isChecked = true
            setPadding(0, 0, 0, 8)
            setOnCheckedChangeListener { _, _ -> autoSave() }
        }
        layout.addView(cbMeow)

        // 自定义后缀
        layout.addView(TextView(this).apply {
            text = "句尾后缀（默认：喵）"
            textSize = 12f
            setTextColor(0xFF999999.toInt())
            setPadding(32, 0, 0, 4)
        })
        etMeowSuffix = EditText(this).apply {
            hint = "喵、唔喵、咩..."
            setPadding(32, 8, 32, 8)
            textSize = 14f
        }
        layout.addView(etMeowSuffix)

        layout.addView(TextView(this).apply {
            text = " " // 间距
            textSize = 4f
        })

        cbWoToBenmiao = CheckBox(this).apply {
            text = "我 →"
            setPadding(0, 0, 0, 8)
            setOnCheckedChangeListener { _, _ -> autoSave() }
        }
        layout.addView(cbWoToBenmiao)

        etWoReplacement = EditText(this).apply {
            hint = "本喵、咱、吾辈、人家..."
            setPadding(48, 8, 32, 8)
            textSize = 14f
        }
        layout.addView(etWoReplacement)

        layout.addView(TextView(this).apply {
            text = " " // 间距
            textSize = 4f
        })

        cbNiToZhuren = CheckBox(this).apply {
            text = "你 →"
            setPadding(0, 0, 0, 8)
            setOnCheckedChangeListener { _, _ -> autoSave() }
        }
        layout.addView(cbNiToZhuren)

        etNiReplacement = EditText(this).apply {
            hint = "主人、杂鱼、笨蛋主人..."
            setPadding(48, 8, 32, 8)
            textSize = 14f
        }
        layout.addView(etNiReplacement)

        layout.addView(TextView(this).apply {
            text = " " // 间距
            textSize = 4f
        })

        cbEmoticon = CheckBox(this).apply {
            text = "随机添加猫颜文字"
            setPadding(0, 0, 0, 8)
            setOnCheckedChangeListener { _, _ -> autoSave() }
        }
        layout.addView(cbEmoticon)

        // ───── 处理模式 ─────
        layout.addView(TextView(this).apply {
            text = "───── 处理模式 ─────"
            textSize = 14f
            setTextColor(0xFF999999.toInt())
            setPadding(0, 32, 0, 12)
        })

        val radioGroup = RadioGroup(this).apply {
            orientation = LinearLayout.VERTICAL
            setOnCheckedChangeListener { _, _ -> autoSave() }
        }
        rbRealtime = RadioButton(this).apply { text = "智能模式"; id = 1 }
        rbPunctuation = RadioButton(this).apply { text = "标点模式"; id = 2 }
        radioGroup.addView(rbRealtime)
        radioGroup.addView(rbPunctuation)
        layout.addView(radioGroup)

        layout.addView(TextView(this).apply {
            text = "空闲延迟秒数（智能模式）："
            textSize = 12f
            setTextColor(0xFF999999.toInt())
            setPadding(32, 8, 0, 4)
        })
        etIdleDelay = EditText(this).apply {
            hint = "1（默认1秒）"
            textSize = 14f
            setPadding(32, 8, 32, 8)
            inputType = android.text.InputType.TYPE_CLASS_NUMBER
        }
        layout.addView(etIdleDelay)

        // ───── 自定义表情 ─────
        layout.addView(TextView(this).apply {
            text = "───── 自定义表情 ─────"
            textSize = 14f
            setTextColor(0xFF999999.toInt())
            setPadding(0, 32, 0, 12)
        })

        etCustomEmoticons = EditText(this).apply {
            hint = "每行一个表情，留空使用默认"
            setLines(4); setMinLines(4); textSize = 14f
            setPadding(0, 8, 0, 8)
        }
        layout.addView(etCustomEmoticons)

        layout.addView(TextView(this).apply {
            text = " " // 间距
            textSize = 8f
        })

        layout.addView(Button(this).apply {
            text = "保存设置"
            setPadding(0, 12, 0, 12)
            setOnClickListener { saveConfig() }
        })

        layout.addView(TextView(this).apply {
            text = "（修改勾选后自动保存，无需手动保存）"
            textSize = 12f
            setTextColor(0xFF999999.toInt())
            setPadding(0, 8, 0, 8)
        })

        // ───── 作者信息 ─────
        layout.addView(TextView(this).apply {
            text = " "
            textSize = 8f
        })

        layout.addView(TextView(this).apply {
            text = "作者：QQ杂鱼助手"
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
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/3975380064-maker/QQZayuHelper"))
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "无法打开浏览器", Toast.LENGTH_SHORT).show()
            }
        }
        layout.addView(tvGithub)

        scrollView.addView(layout)
        setContentView(scrollView)

        // 加载配置
        loadConfig()
    }

    override fun onResume() {
        super.onResume()
        updateServiceStatus()
    }

    private fun updateServiceStatus() {
        val enabled = isAccessibilityServiceEnabled()
        tvStatus.text = if (enabled) "服务状态：已开启 ✅" else "服务状态：未开启 ❌"
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
                    // 部分设备不支持，跳转系统设置
                    startActivity(Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS))
                }
            } else {
                Toast.makeText(this, "已在电池优化白名单中", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Android 6.0+ 才支持此功能", Toast.LENGTH_SHORT).show()
        }
    }

    private fun requestWakeLock() {
        // WAKE_LOCK 是普通权限，声明即自动授权
        // 无障碍服务已持有 WAKE_LOCK，这里告知用户
        AlertDialog.Builder(this)
            .setTitle("保持唤醒（WAKE_LOCK）")
            .setMessage("WAKE_LOCK 权限已声明（普通权限，安装时自动授权）。\n\n无障碍服务运行期间会持有唤醒锁，防止屏幕关闭后 CPU 休眠导致功能暂停。")
            .setPositiveButton("知道了", null)
            .show()
    }

    private fun autoSave() {
        saveConfig()
    }

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