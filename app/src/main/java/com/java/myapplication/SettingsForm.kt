package com.java.myapplication

import android.view.LayoutInflater
import android.view.View
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.java.myapplication.databinding.ActivityMainBinding
import com.java.myapplication.databinding.ItemRuleBinding

/**
 * 设置表单：把布局里的控件与 CatConfig 双向绑定。
 *
 * 自定义替换规则是动态列表（增 / 删），这里同时负责把它序列化成
 * "原词=替换词"的行格式——该格式与旧版本一致，老用户的设置不会丢。
 *
 * 回填期间 [isLoading] 为 true，避免 setText/setChecked 触发监听器造成反复保存。
 */
class SettingsForm(
    private val binding: ActivityMainBinding,
    private val inflater: LayoutInflater,
    private val onChanged: () -> Unit
) {

    var isLoading = false
        private set

    private val ruleRows = mutableListOf<ItemRuleBinding>()

    private val switches get() = binding.sectionSwitches
    private val rulesSection get() = binding.sectionRules
    private val modeSection get() = binding.sectionMode

    init {
        val editors = listOf(
            switches.etMeowSuffix,
            switches.etWoReplacement,
            switches.etNiReplacement,
            switches.etPrefix,
            modeSection.etIdleDelay,
            binding.sectionEmoticons.etCustomEmoticons
        )
        editors.forEach { editor -> editor.doAfterTextChanged { notifyChanged() } }

        switches.switchEnabled.setOnCheckedChangeListener { _, _ -> notifyChanged() }
        switches.switchMeow.setOnCheckedChangeListener { _, _ -> notifyChanged() }
        switches.switchWo.setOnCheckedChangeListener { _, _ -> notifyChanged() }
        switches.switchNi.setOnCheckedChangeListener { _, _ -> notifyChanged() }
        switches.switchEmoticon.setOnCheckedChangeListener { _, _ -> notifyChanged() }
        switches.switchPrefix.setOnCheckedChangeListener { _, _ -> notifyChanged() }

        modeSection.modeToggle.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (!isChecked) return@addOnButtonCheckedListener
            applyModeUi(checkedId == R.id.btnModeRealtime)
            notifyChanged()
        }

        rulesSection.btnAddRule.setOnClickListener { addRuleRow("", "") }
    }

    private fun notifyChanged() {
        if (!isLoading) onChanged()
    }

    // ── 规则列表 ──

    private fun addRuleRow(from: String, to: String) {
        val row = ItemRuleBinding.inflate(inflater, rulesSection.rulesContainer, false)
        row.etRuleFrom.setText(from)
        row.etRuleTo.setText(to)
        row.etRuleFrom.doAfterTextChanged { notifyChanged() }
        row.etRuleTo.doAfterTextChanged { notifyChanged() }
        row.btnDeleteRule.setOnClickListener { removeRuleRow(row) }
        rulesSection.rulesContainer.addView(row.root)
        ruleRows.add(row)
        updateRulesEmptyState()
    }

    private fun removeRuleRow(row: ItemRuleBinding) {
        rulesSection.rulesContainer.removeView(row.root)
        ruleRows.remove(row)
        updateRulesEmptyState()
        notifyChanged()
    }

    private fun updateRulesEmptyState() {
        rulesSection.tvRulesEmpty.visibility =
            if (ruleRows.isEmpty()) View.VISIBLE else View.GONE
    }

    // ── 绑定 ──

    /** 把配置回填到控件。 */
    fun bind(config: CatConfig) {
        isLoading = true

        with(switches) {
            switchEnabled.isChecked = config.enabled
            switchMeow.isChecked = config.enableMeow
            switchWo.isChecked = config.enableWoToBenmiao
            switchNi.isChecked = config.enableNiToZhuren
            switchEmoticon.isChecked = config.enableRandomEmoticon
            switchPrefix.isChecked = config.enablePrefix
            etMeowSuffix.setText(config.meowSuffix)
            etWoReplacement.setText(config.woReplacement)
            etNiReplacement.setText(config.niReplacement)
            etPrefix.setText(config.prefixText)
        }

        binding.sectionEmoticons.etCustomEmoticons.setText(config.customEmoticons.joinToString("\n"))

        val realtime = config.processingMode == CatConfig.REAL_TIME_MODE
        modeSection.modeToggle.check(if (realtime) R.id.btnModeRealtime else R.id.btnModePunctuation)
        modeSection.etIdleDelay.setText(config.idleDelayMs.toString())
        // check() 若命中同一个按钮不会回调监听器，这里显式同步一次界面状态
        applyModeUi(realtime)

        ruleRows.toList().forEach { rulesSection.rulesContainer.removeView(it.root) }
        ruleRows.clear()
        config.customRules.forEach { raw ->
            val parsed = ReplaceRules.parseRule(raw) ?: return@forEach
            addRuleRow(parsed.first, parsed.second)
        }
        updateRulesEmptyState()

        isLoading = false
    }

    /** 把控件上的值读进 config。 */
    fun readInto(config: CatConfig) {
        with(switches) {
            config.enabled = switchEnabled.isChecked
            config.enableMeow = switchMeow.isChecked
            config.enableWoToBenmiao = switchWo.isChecked
            config.enableNiToZhuren = switchNi.isChecked
            config.enableRandomEmoticon = switchEmoticon.isChecked
            config.enablePrefix = switchPrefix.isChecked
            // 替换词留空时保留原值，避免用户清空输入框把功能弄坏
            textOrNull(etWoReplacement)?.let { config.woReplacement = it }
            textOrNull(etNiReplacement)?.let { config.niReplacement = it }
            textOrNull(etMeowSuffix)?.let { config.meowSuffix = it }
            textOrNull(etPrefix)?.let { config.prefixText = it }
        }

        config.processingMode =
            if (modeSection.modeToggle.checkedButtonId == R.id.btnModeRealtime) {
                CatConfig.REAL_TIME_MODE
            } else {
                CatConfig.PUNCTUATION_MODE
            }
        textOrNull(modeSection.etIdleDelay)?.toIntOrNull()?.let {
            if (it > 0) config.idleDelayMs = it
        }

        config.customEmoticons = splitLines(binding.sectionEmoticons.etCustomEmoticons)

        config.customRules = ruleRows.mapNotNull { row ->
            val from = row.etRuleFrom.text?.toString()?.trim().orEmpty()
            val to = row.etRuleTo.text?.toString()?.trim().orEmpty()
            if (from.isNotEmpty() && to.isNotEmpty()) "$from=$to" else null
        }.toTypedArray()
    }

    private fun applyModeUi(realtime: Boolean) {
        modeSection.tvModeSummary.setText(
            if (realtime) R.string.mode_realtime_summary else R.string.mode_punctuation_summary
        )
        // 延迟只在智能模式下有意义，标点模式下直接收起来
        modeSection.idleRow.visibility = if (realtime) View.VISIBLE else View.GONE
    }

    private fun splitLines(editor: TextInputEditText): Array<String> =
        editor.text.toString().split("\n").filter { it.isNotBlank() }.toTypedArray()

    private fun textOrNull(editor: TextInputEditText): String? =
        editor.text.toString().trim().ifEmpty { null }
}