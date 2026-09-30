package com.java.myapplication

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.google.android.material.button.MaterialButton
import com.google.android.material.radiobutton.MaterialRadioButton
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

/**
 * 界面工厂：集中界面配色与控件构造。
 * 只负责“造控件、套样式”，不持有业务状态，也不碰配置读写。
 */
class UiKit(private val context: Context) {

    val colorBackground = 0xFFF5F5F7.toInt()
    val colorCard = 0xFFFFFFFF.toInt()
    val colorPrimary = 0xFF007AFF.toInt()
    val colorTextPrimary = 0xFF1C1C1E.toInt()
    val colorTextSecondary = 0xFF8E8E93.toInt()
    val colorDivider = 0xFFE5E5EA.toInt()
    val colorSuccess = 0xFF34C759.toInt()
    val colorWarning = 0xFFFF3B30.toInt()

    fun createCard(): CardView = CardView(context).apply {
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(0, 0, 0, 16)
        }
        radius = 16f
        cardElevation = 0f
        setCardBackgroundColor(colorCard)
    }

    fun createSectionTitle(text: String): TextView = TextView(context).apply {
        this.text = text
        textSize = 13f
        setTextColor(colorTextSecondary)
        setTypeface(typeface, Typeface.BOLD)
        setPadding(12, 16, 0, 8)
    }

    fun createScreenTitle(text: String): TextView = TextView(context).apply {
        this.text = text
        textSize = 28f
        setTextColor(colorTextPrimary)
        setTypeface(typeface, Typeface.BOLD)
        layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
    }

    /** 版本号小徽标（灰底圆角）。 */
    fun createVersionBadge(text: String): TextView = TextView(context).apply {
        this.text = text
        textSize = 14f
        setTextColor(colorTextSecondary)
        setPadding(8, 4, 8, 4)
        background = GradientDrawable().apply {
            cornerRadius = 12f
            setColor(colorDivider)
        }
    }

    /** 说明性小灰字。 */
    fun createLabel(text: String, bottomPadding: Int = 8): TextView = TextView(context).apply {
        this.text = text
        textSize = 13f
        setTextColor(colorTextSecondary)
        setPadding(0, 0, 0, bottomPadding)
    }

    fun createSwitch(text: String, checked: Boolean, onChanged: () -> Unit): SwitchMaterial =
        SwitchMaterial(context).apply {
            this.text = text
            isChecked = checked
            textSize = 16f
            setTextColor(colorTextPrimary)
            setPadding(16, 16, 16, 16)
            setOnCheckedChangeListener { _, _ -> onChanged() }
        }

    fun createRadioGroup(onChanged: () -> Unit): RadioGroup = RadioGroup(context).apply {
        orientation = RadioGroup.VERTICAL
        setOnCheckedChangeListener { _, _ -> onChanged() }
    }

    fun createRadioButton(text: String): MaterialRadioButton = MaterialRadioButton(context).apply {
        this.text = text
        textSize = 15f
        setTextColor(colorTextPrimary)
        // 必须用生成的 id，硬编码 int 会被 lint 判定为资源类型错误
        id = View.generateViewId()
    }

    fun createTextInputRow(hint: String, helper: String): Pair<TextInputEditText, TextInputLayout> {
        val edit = TextInputEditText(context).apply {
            textSize = 15f
            setTextColor(colorTextPrimary)
            setPadding(0, 8, 0, 8)
        }
        val layout = TextInputLayout(context).apply {
            this.hint = hint
            setHelperText(helper)
            setHelperTextColor(android.content.res.ColorStateList.valueOf(colorTextSecondary))
            boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_NONE
            setPadding(16, 4, 16, 8)
            addView(edit)
        }
        return Pair(edit, layout)
    }

    /** 无边框多行输入框（自定义表情/规则用）。 */
    fun createMultilineEditor(lines: Int): TextInputEditText = TextInputEditText(context).apply {
        setLines(lines)
        minLines = lines
        textSize = 15f
        setTextColor(colorTextPrimary)
        background = null
        gravity = Gravity.TOP
        setPadding(0, 8, 0, 8)
    }

    fun createOutlineButton(text: String, onClick: () -> Unit): MaterialButton =
        MaterialButton(context, null, 0).apply {
            this.text = text
            textSize = 16f
            setTextColor(colorPrimary)
            strokeColor = android.content.res.ColorStateList.valueOf(colorPrimary)
            strokeWidth = 2
            cornerRadius = 24
            setBackgroundColor(0x00000000)
            elevation = 0f
            stateListAnimator = null
            gravity = Gravity.CENTER
            setOnClickListener { onClick() }
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

    fun createFilledButton(text: String, onClick: () -> Unit): MaterialButton =
        MaterialButton(context, null, 0).apply {
            this.text = text
            textSize = 16f
            setTextColor(0xFFFFFFFF.toInt())
            cornerRadius = 24
            setBackgroundColor(colorPrimary)
            elevation = 0f
            stateListAnimator = null
            gravity = Gravity.CENTER
            setOnClickListener { onClick() }
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

    /** 左对齐的整行文字按钮。 */
    fun createTextButton(text: String, onClick: () -> Unit): MaterialButton =
        MaterialButton(context, null, 0).apply {
            this.text = text
            textSize = 15f
            setTextColor(colorTextPrimary)
            setBackgroundColor(0x00000000)
            elevation = 0f
            stateListAnimator = null
            setOnClickListener { onClick() }
            gravity = Gravity.START or Gravity.CENTER_VERTICAL
            setPadding(32, 28, 32, 28)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            rippleColor = android.content.res.ColorStateList.valueOf(0x1F000000)
        }

    fun createThinDivider(): View = View(context).apply {
        setBackgroundColor(colorDivider)
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, 1
        ).apply {
            setMargins(32, 0, 0, 0)
        }
    }

    fun spacerView(heightDp: Int): View = View(context).apply {
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, heightDp
        )
    }

    /** 服务状态圆点。 */
    fun createStatusDot(color: Int): View = View(context).apply {
        layoutParams = LinearLayout.LayoutParams(10, 10).apply {
            setMargins(0, 0, 12, 0)
        }
        background = circleDrawable(color)
    }

    fun circleDrawable(color: Int): GradientDrawable = GradientDrawable().apply {
        shape = GradientDrawable.OVAL
        setColor(color)
    }
}