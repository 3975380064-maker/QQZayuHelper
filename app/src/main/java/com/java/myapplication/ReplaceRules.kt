package com.java.myapplication

/**
 * 文本替换规则：把用户原文变换成“杂鱼语”，以及把它还原回用户原文。
 *
 * 之前这段逻辑散落在 TextProcessor、TextReplaceEngine.applyReplacementsOnly、
 * TextReplaceEngine.stripEngineOutput 三处，改一条规则要同步改三处，很容易发散。
 * 现在统一到这里，并保证正向 [apply] 与逆向 [revert] 严格互逆。
 *
 * 正向顺序固定：我→替换词，你→替换词，然后按列表顺序应用自定义规则。
 * 逆向按相反顺序还原。自定义规则之间可能链式覆盖（如 说=曰、曰=云），
 * 所以逆向时先用占位符把词锁住再统一展开，避免还原错误。
 */
object ReplaceRules {

    /** 私有区占位符，正常聊天文本不会出现，避免与用户输入冲突。 */
    private const val PLACEHOLDER_WO = "\ue000"
    private const val PLACEHOLDER_NI = "\ue001"

    /** 解析 "原词=替换词"，非法规则返回 null。 */
    fun parseRule(raw: String): Pair<String, String>? {
        val parts = raw.split("=", limit = 2)
        if (parts.size != 2) return null
        val from = parts[0]
        val to = parts[1]
        if (from.isBlank() || to.isBlank()) return null
        return Pair(from, to)
    }

    /** 应用全部替换规则（不含句尾后缀与颜文字）。 */
    fun apply(text: String, cfg: CatConfig): String {
        var result = text
        if (cfg.enableWoToBenmiao && cfg.woReplacement.isNotEmpty()) {
            result = result.replace("我", cfg.woReplacement)
        }
        if (cfg.enableNiToZhuren && cfg.niReplacement.isNotEmpty()) {
            result = result.replace("你", cfg.niReplacement)
        }
        for (rule in cfg.customRules) {
            val parsed = parseRule(rule) ?: continue
            result = result.replace(parsed.first, parsed.second)
        }
        return result
    }

    /** [apply] 的逆操作：把引擎加工过的文本还原成用户原文。 */
    fun revert(text: String, cfg: CatConfig): String {
        if (text.isEmpty()) return text
        var result = text

        // 1. 自定义规则：严格按“应用顺序的逆序”逐条还原。
        //    链式规则（说=曰、曰=云）必须逆序才能还原：先把 云 还原成 曰，再还原成 说。
        //    这里不能用延迟展开的占位符，否则上一步的中间结果会被占位符挡住，
        //    后一条规则就匹配不到了。
        for (rule in cfg.customRules.mapNotNull { parseRule(it) }.reversed()) {
            result = result.replace(rule.second, rule.first)
        }

        // 2. 我 / 你
        if (cfg.enableNiToZhuren && cfg.niReplacement.isNotEmpty()) {
            result = result.replace(cfg.niReplacement, PLACEHOLDER_NI)
        }
        if (cfg.enableWoToBenmiao && cfg.woReplacement.isNotEmpty()) {
            result = result.replace(cfg.woReplacement, PLACEHOLDER_WO)
        }
        return result.replace(PLACEHOLDER_WO, "我").replace(PLACEHOLDER_NI, "你")
    }
}