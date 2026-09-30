package com.java.myapplication

import java.util.regex.Pattern

/**
 * 文本处理器：把用户原文加工成最终写回输入框的文本。
 * 顺序固定：替换词（[ReplaceRules]）→ 句尾后缀 → 颜文字。
 * 纯函数，不持有状态，便于单元测试。
 */
object TextProcessor {

    private val SENTENCE_SPLIT_PATTERN = Pattern.compile("([，。！？\\s]+)")

    private fun addMeow(text: String, suffix: String): String {
        val parts = mutableListOf<String>()
        val separators = mutableListOf<String>()
        val matcher = SENTENCE_SPLIT_PATTERN.matcher(text)
        var lastEnd = 0
        while (matcher.find()) {
            val before = text.substring(lastEnd, matcher.start())
            val sep = matcher.group(1) ?: ""
            parts.add(before)
            separators.add(sep)
            lastEnd = matcher.end()
        }
        if (lastEnd < text.length) {
            parts.add(text.substring(lastEnd))
        } else if (parts.isNotEmpty() && lastEnd == text.length) {
            parts.add("")
        }
        if (parts.isEmpty()) {
            parts.add(text)
        }
        val result = StringBuilder()
        for (i in parts.indices) {
            val part = parts[i].trim()
            if (part.isNotEmpty()) {
                result.append(part)
                // 该片段末尾已有后缀就不再重复添加
                if (!part.endsWith(suffix)) {
                    result.append(suffix)
                }
            }
            if (i < separators.size) {
                result.append(separators[i])
            }
        }
        var resultStr = result.toString().trim()
        if (resultStr.isEmpty()) {
            resultStr = "$text$suffix"
        }
        return resultStr
    }

    /**
     * [addMeow] 的逆操作：剥掉每个句段末尾的后缀，还原用户原文。
     *
     * 必须和 addMeow 完全对称 —— addMeow 会给“每一段”都加后缀，
     * 如果这里只剥末尾一个，中间那些就会残留成用户原文的一部分，
     * 下一次写入再加一遍后缀，就会越滚越多（如 你好喵，吃饭喵 → 你好喵喵，吃饭喵）。
     */
    fun stripMeowSuffix(text: String, cfg: CatConfig): String {
        if (!cfg.enableMeow || cfg.meowSuffix.isEmpty()) return text.trim()
        val pattern = Regex("${Regex.escape(cfg.meowSuffix)}(?=[，。！？\\s]|\$)")
        return text.replace(pattern, "").trim()
    }

    /**
     * 根据文本内容确定性地选择颜文字。
     * 用文本内容做种子而不是随机数，避免同一段文本每次处理得到不同装饰、
     * 进而反复写回输入框打断用户输入。
     */
    private fun getRandomEmoticon(seed: String, config: CatConfig): String {
        val emoticons = config.getActiveEmoticons()
        if (emoticons.isEmpty()) return ""
        val index = seed.hashCode().and(Int.MAX_VALUE) % emoticons.size
        return emoticons[index]
    }

    /** 把用户原文加工成最终文本。 */
    fun process(original: String, config: CatConfig): String {
        if (original.isBlank()) return original

        var text = original.trim()
        text = ReplaceRules.apply(text, config)
        if (config.enableMeow) {
            text = addMeow(text, config.meowSuffix)
        }
        if (config.enableRandomEmoticon) {
            // 文本末尾已有有效颜文字就保留，不重新选
            val emoticons = config.getActiveEmoticons()
            val alreadyHasEmoticon = emoticons.any { em ->
                text.endsWith(" $em") || text.endsWith(em)
            }
            if (!alreadyHasEmoticon) {
                val emoticon = getRandomEmoticon(text, config)
                if (emoticon.isNotEmpty()) {
                    text = "$text $emoticon"
                }
            }
        }
        return text
    }
}