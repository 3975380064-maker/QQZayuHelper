package com.java.myapplication

import android.text.InputType
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.radiobutton.MaterialRadioButton
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

/**
 * 设置表单：持有全部配置控件，并负责 CatConfig 与控件之间的双向绑定。
 *
 * 这里只做“控件 <-> 配置”的映射，不关心这些控件被摆在哪、也不关心保存到哪。
 * [bind] 期间会把 [isLoading] 置位，避免回填触发监听器造成重复保存。
 */
class SettingsForm(private val ui: UiKit, private val onChanged: () -> Unit) {

    var isLoading = false
        private set

    val switchEnabled = ui.createSwitch("启用文字替换功能", true, ::notifyChanged)

    val switchMeow = ui.createSwitch("句尾添加后缀", true, ::notifyChanged)
    val meowRow: TextInputLayout
    val etMeowSuffix: TextInputEditText

    val switchWoToBenmiao = ui.createSwitch("替换「我」", true, ::notifyChanged)
    val woRow: TextInputLayout
    val etWoReplacement: TextInputEditText

    val switchNiToZhuren = ui.createSwitch("替换「你」", true, ::notifyChanged)
    val niRow: TextInputLayout
    val etNiReplacement: TextInputEditText

    val switchEmoticon = ui.createSwitch("随机添加后缀表情", true, ::notifyChanged)

    val radioGroup = ui.createRadioGroup(::notifyChanged)
    val rbRealtime = ui.createRadioButton("智能模式")
    val rbPunctuation = ui.createRadioButton("标点模式")

    val delayRow: TextInputLayout
    val etIdleDelay: TextInputEditText

    val etCustomEmoticons = ui.createMultilineEditor(4)
    val etCustomRules = ui.createMultilineEditor(3)

    init {
        val (meowEdit, meowLayout) = ui.createTextInputRow("句尾后缀", "喵、唔喵、咩...")
        etMeowSuffix = meowEdit
        meowRow = meowLayout

        val (woEdit, woLayout) = ui.createTextInputRow("替换为", "本喵、咱、吾辈、人家...")
        etWoReplacement = woEdit
        woRow = woLayout

        val (niEdit, niLayout) = ui.createTextInputRow("替换为", "主人、杂鱼、笨蛋主人...")
        etNiReplacement = niEdit
        niRow = niLayout

        val (delayEdit, delayLayout) = ui.createTextInputRow("空闲延迟（毫秒，仅智能模式）", "1000")
        etIdleDelay = delayEdit
        etIdleDelay.inputType = InputType.TYPE_CLASS_NUMBER
        delayRow = delayLayout

        radioGroup.addView(rbRealtime)
        radioGroup.addView(rbPunctuation)

        attachAutoSave(
            etMeowSuffix, etWoReplacement, etNiReplacement,
            etIdleDelay, etCustomEmoticons, etCustomRules
        )
    }

    private fun notifyChanged() {
        if (!isLoading) onChanged()
    }

    /** 给文本框挂自动保存。回填时 isLoading 为 true，不会触发保存。 */
    private fun attachAutoSave(vararg editors: TextInputEditText) {
        editors.forEach { editor ->
            editor.doAfterTextChanged { notifyChanged() }
        }
    }

    /** 把配置回填到控件。 */
    fun bind(config: CatConfig) {
        isLoading = true
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
        // idleDelayMs 存的就是毫秒，界面直接显示
        etIdleDelay.setText(config.idleDelayMs.toString())
        isLoading = false
    }

    /** 把控件上的值读进 config。 */
    fun readInto(config: CatConfig) {
        config.enabled = switchEnabled.isChecked
        config.enableMeow = switchMeow.isChecked
        config.enableWoToBenmiao = switchWoToBenmiao.isChecked
        config.enableNiToZhuren = switchNiToZhuren.isChecked
        config.enableRandomEmoticon = switchEmoticon.isChecked
        config.processingMode =
            if (rbRealtime.isChecked) CatConfig.REAL_TIME_MODE else CatConfig.PUNCTUATION_MODE
        config.customEmoticons = splitLines(etCustomEmoticons)
        config.customRules = splitLines(etCustomRules)

        // 替换词留空时保留原值，避免用户清空输入框把功能弄坏
        textOrNull(etWoReplacement)?.let { config.woReplacement = it }
        textOrNull(etNiReplacement)?.let { config.niReplacement = it }
        textOrNull(etMeowSuffix)?.let { config.meowSuffix = it }
        textOrNull(etIdleDelay)?.toIntOrNull()?.let {
            if (it > 0) config.idleDelayMs = it
        }
    }

    private fun splitLines(editor: TextInputEditText): Array<String> =
        editor.text.toString().split("\n").filter { it.isNotBlank() }.toTypedArray()

    private fun textOrNull(editor: TextInputEditText): String? =
        editor.text.toString().trim().ifEmpty { null }
}