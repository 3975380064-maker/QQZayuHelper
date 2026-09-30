package com.java.myapplication

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.google.android.material.button.MaterialButton

/** 界面上需要由 Activity 回调的动作。 */
interface MainUiCallbacks {
    fun onOpenAccessibilitySettings()
    fun onRequestBatteryOptimization()
    fun onManageAutoStart()
    fun onCheckUpdate()
    fun onSave()
}

/** 构建完成后仍需在运行时更新的控件。 */
class MainView(
    val root: View,
    val statusDot: View,
    val tvStatus: TextView,
    val btnOpenSettings: MaterialButton,
    val btnCheckUpdate: MaterialButton
)

/**
 * 主界面构建：只负责把控件摆成最终布局并接线回调。
 * 不读写配置、不处理业务逻辑。
 */
class MainActivityUi(
    private val activity: Activity,
    private val ui: UiKit,
    private val form: SettingsForm,
    private val callbacks: MainUiCallbacks
) {

    fun build(versionName: String): MainView {
        val scrollView = ScrollView(activity).apply {
            setBackgroundColor(ui.colorBackground)
        }
        val rootLayout = LinearLayout(activity).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 24, 24, 48)
        }

        rootLayout.addView(buildHeader(versionName))

        val statusCard = buildStatusCard()
        rootLayout.addView(statusCard.card)
        rootLayout.addView(buildMainSwitchCard())

        rootLayout.addView(ui.createSectionTitle("权限与续航"))
        rootLayout.addView(buildPermissionCard())

        rootLayout.addView(ui.createSectionTitle("替换规则"))
        rootLayout.addView(buildReplaceCard())

        rootLayout.addView(ui.createSectionTitle("处理模式"))
        rootLayout.addView(buildModeCard())

        rootLayout.addView(ui.createSectionTitle("自定义表情"))
        rootLayout.addView(buildEmoticonCard())

        rootLayout.addView(ui.createSectionTitle("自定义前缀"))
        rootLayout.addView(buildPrefixCard())

        rootLayout.addView(ui.createSectionTitle("自定义替换规则"))
        rootLayout.addView(buildRulesCard())

        rootLayout.addView(ui.createSectionTitle("更新"))
        val updateCard = buildUpdateCard()
        rootLayout.addView(updateCard.card)

        rootLayout.addView(buildSaveArea())
        rootLayout.addView(buildFooter())

        scrollView.addView(rootLayout)
        return MainView(
            root = scrollView,
            statusDot = statusCard.dot,
            tvStatus = statusCard.textView,
            btnOpenSettings = statusCard.button,
            btnCheckUpdate = updateCard.button
        )
    }

    private fun buildHeader(versionName: String): View {
        val header = LinearLayout(activity).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(8, 16, 8, 24)
        }
        header.addView(ui.createScreenTitle("杂鱼助手"))
        header.addView(ui.createVersionBadge("v$versionName"))
        return header
    }

    private class StatusCard(
        /** 要挂进根布局的卡片本身 */
        val card: CardView,
        val dot: View,
        val textView: TextView,
        val button: MaterialButton
    )

    private fun buildStatusCard(): StatusCard {
        val card = ui.createCard()
        val statusLayout = LinearLayout(activity).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 20, 24, 20)
        }
        val statusRow = LinearLayout(activity).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }
        val statusDot = ui.createStatusDot(ui.colorWarning)
        val tvStatus = TextView(activity).apply {
            text = "服务状态：未开启"
            textSize = 15f
            setTextColor(ui.colorTextPrimary)
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        }
        statusRow.addView(statusDot)
        statusRow.addView(tvStatus)

        val btnOpenSettings = ui.createOutlineButton("前往系统设置开启无障碍") {
            callbacks.onOpenAccessibilitySettings()
        }
        btnOpenSettings.visibility = View.GONE

        statusLayout.addView(statusRow)
        statusLayout.addView(ui.spacerView(12))
        statusLayout.addView(btnOpenSettings)
        card.addView(statusLayout)
        return StatusCard(card, statusDot, tvStatus, btnOpenSettings)
    }

    private fun buildMainSwitchCard(): View {
        val card = ui.createCard()
        val layout = LinearLayout(activity).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 16, 24, 16)
        }
        layout.addView(form.switchEnabled)
        card.addView(layout)
        return card
    }

    private fun buildPermissionCard(): View {
        val card = ui.createCard()
        val layout = LinearLayout(activity).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(8, 8, 8, 8)
        }
        val btnBatteryOpt = ui.createTextButton("申请电池优化白名单") {
            callbacks.onRequestBatteryOptimization()
        }
        val btnAutoStart = ui.createTextButton("管理自启动") {
            callbacks.onManageAutoStart()
        }
        layout.addView(btnBatteryOpt)
        layout.addView(ui.createThinDivider())
        layout.addView(btnAutoStart)
        card.addView(layout)
        return card
    }

    private fun buildReplaceCard(): View {
        val card = ui.createCard()
        val layout = LinearLayout(activity).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(8, 8, 8, 8)
        }
        layout.addView(form.switchMeow)
        layout.addView(form.meowRow)
        layout.addView(ui.createThinDivider())
        layout.addView(form.switchWoToBenmiao)
        layout.addView(form.woRow)
        layout.addView(ui.createThinDivider())
        layout.addView(form.switchNiToZhuren)
        layout.addView(form.niRow)
        layout.addView(ui.createThinDivider())
        layout.addView(form.switchEmoticon)
        layout.addView(ui.createThinDivider())
        layout.addView(form.switchPrefix)
        card.addView(layout)
        return card
    }

    private fun buildModeCard(): View {
        val card = ui.createCard()
        val layout = LinearLayout(activity).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 16, 24, 16)
        }
        layout.addView(form.radioGroup)
        layout.addView(ui.spacerView(12))
        layout.addView(form.delayRow)
        card.addView(layout)
        return card
    }

    private fun buildEmoticonCard(): View {
        val card = ui.createCard()
        val layout = LinearLayout(activity).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 16, 24, 16)
        }
        layout.addView(ui.createLabel("每行一个，留空则使用默认表情"))
        layout.addView(
            form.etCustomEmoticons,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        )
        card.addView(layout)
        return card
    }

    private fun buildPrefixCard(): View {
        val card = ui.createCard()
        val layout = LinearLayout(activity).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 16, 24, 16)
        }
        layout.addView(ui.createLabel("每行一个，留空则使用默认前缀"))
        layout.addView(
            form.etCustomPrefixes,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        )
        card.addView(layout)
        return card
    }

    private fun buildRulesCard(): View {
        val card = ui.createCard()
        val layout = LinearLayout(activity).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 16, 24, 16)
        }
        layout.addView(ui.createLabel("每行一条，格式：原词=替换词\n例如：说=曰、吗=嘛"))
        layout.addView(
            form.etCustomRules,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        )
        card.addView(layout)
        return card
    }

    private class ButtonCard(val card: View, val button: MaterialButton)

    private fun buildUpdateCard(): ButtonCard {
        val card = ui.createCard()
        val layout = LinearLayout(activity).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(8, 8, 8, 8)
        }
        val button = ui.createTextButton("检查更新") { callbacks.onCheckUpdate() }
        layout.addView(button)
        card.addView(layout)
        return ButtonCard(card, button)
    }

    private fun buildSaveArea(): View {
        val container = LinearLayout(activity).apply {
            orientation = LinearLayout.VERTICAL
        }
        val saveBtn = ui.createFilledButton("保存设置") { callbacks.onSave() }
        val saveHint = TextView(activity).apply {
            text = "修改后自动保存，也可手动点击保存"
            textSize = 12f
            setTextColor(ui.colorTextSecondary)
            gravity = Gravity.CENTER
            setPadding(0, 8, 0, 0)
        }
        container.addView(ui.spacerView(16))
        container.addView(saveBtn)
        container.addView(saveHint)
        return container
    }

    private fun buildFooter(): View {
        val container = LinearLayout(activity).apply {
            orientation = LinearLayout.VERTICAL
        }
        val tvAuthor = TextView(activity).apply {
            text = "作者：喵喵喵"
            textSize = 13f
            setTextColor(ui.colorTextSecondary)
            gravity = Gravity.CENTER
        }
        val tvGithub = TextView(activity).apply {
            text = "github.com/3975380064-maker/QQZayuHelper"
            textSize = 13f
            setTextColor(ui.colorPrimary)
            gravity = Gravity.CENTER
            setPadding(0, 8, 0, 0)
            setOnClickListener {
                try {
                    activity.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://github.com/3975380064-maker/QQZayuHelper")
                        )
                    )
                } catch (e: Exception) {
                    Toast.makeText(activity, "无法打开浏览器", Toast.LENGTH_SHORT).show()
                }
            }
        }
        container.addView(ui.spacerView(32))
        container.addView(tvAuthor)
        container.addView(tvGithub)
        return container
    }
}