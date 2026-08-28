package com.java.myapplication

import java.util.Random
import java.util.regex.Pattern

object TextProcessor {

    private val RANDOM = Random()
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
                result.append(suffix)
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

    private fun getRandomEmoticon(config: CatConfig): String {
        val emoticons = config.getActiveEmoticons()
        if (emoticons.isEmpty()) return ""
        return emoticons[RANDOM.nextInt(emoticons.size)]
    }

    fun process(original: String, config: CatConfig): String {
        if (original.isNullOrBlank()) return original

        var text = original.trim()

        if (config.enableWoToBenmiao) {
            text = text.replace("我", config.woReplacement)
        }
        if (config.enableNiToZhuren) {
            text = text.replace("你", config.niReplacement)
        }
        // 自定义替换规则
        text = applyCustomRules(text, config)
        if (config.enableMeow) {
            text = addMeow(text, config.meowSuffix)
        }
        if (config.enableRandomEmoticon) {
            val emoticon = getRandomEmoticon(config)
            if (emoticon.isNotEmpty()) {
                text = "$text $emoticon"
            }
        }
        return text
    }

    /**
     * 应用自定义替换规则。
     * 每条规则格式 "原词=替换词"，按配置顺序逐条替换。
     */
    private fun applyCustomRules(text: String, config: CatConfig): String {
        if (config.customRules.isEmpty()) return text
        var result = text
        for (rule in config.customRules) {
            val parts = rule.split("=", limit = 2)
            if (parts.size == 2 && parts[0].isNotBlank()) {
                result = result.replace(parts[0], parts[1])
            }
        }
        return result
    }
}