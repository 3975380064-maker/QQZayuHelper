package com.java.myapplication

import java.util.regex.Pattern

/**
 * 文本处理器：把用户原文加工成最终写回输入框的文本。
 * 顺序固定：替换词（[ReplaceRules]）→ 句尾后缀 → 颜文字。
 * 纯函数，不持有状态，便于单元测试。
 */
object TextProcessor {

    private val SENTENCE_SPLIT_PATTERN = Pattern.compile("([，。！？\\s]+)")

    /** 前缀选择用的盐，避免和颜文字的哈希选到同一个下标。 */
    private const val PREFIX_SEED_SALT = "\u0000prefix"

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
     * 返回末尾“装饰”（句尾后缀 + 颜文字）的起始下标。
     *
     * 光标映射时用：光标如果落在装饰里，说明用户并不是想在中间插字，
     * 直接按内容末尾处理即可，否则会把颜文字截成半个。
     */
    fun contentEnd(text: String, cfg: CatConfig): Int {
        var end = text.length
        if (cfg.enableRandomEmoticon) {
            for (em in cfg.getActiveEmoticons().sortedByDescending { it.length }) {
                val spaced = " $em"
                if (text.endsWith(spaced) && text.length > spaced.length) {
                    end = text.length - spaced.length
                    break
                }
                if (text.endsWith(em) && text.length > em.length) {
                    end = text.length - em.length
                    break
                }
            }
        }
        if (cfg.enableMeow && cfg.meowSuffix.isNotEmpty() && end >= cfg.meowSuffix.length) {
            val start = end - cfg.meowSuffix.length
            if (text.startsWith(cfg.meowSuffix, start)) {
                end = start
            }
        }
        return end
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

    /** 同上，确定性选择开头前缀。加盐是为了让它和颜文字的选择互不相关。 */
    private fun pickPrefix(seed: String, config: CatConfig): String {
        val prefixes = config.getActivePrefixes()
        if (prefixes.isEmpty()) return ""
        val index = (seed + PREFIX_SEED_SALT).hashCode().and(Int.MAX_VALUE) % prefixes.size
        return prefixes[index]
    }

    /**
     * 取出文本开头已有的前缀；没有则返回 null。
     *
     * 这条消息一旦加过前缀，后续触发就应该沿用它而不是重新挑，
     * 否则用户每续写一句，前缀都会在眼皮底下跳变。
     */
    fun existingPrefix(text: String, cfg: CatConfig): String? {
        if (!cfg.enablePrefix) return null
        val start = text.indexOfFirst { !it.isWhitespace() }
        if (start < 0) return null
        for (prefix in cfg.getActivePrefixes().sortedByDescending { it.length }) {
            if (text.startsWith(prefix, start)) return prefix
        }
        return null
    }

    /** 开头前缀的结束下标（跳过前导空白后匹配）；没有前缀返回 0。 */
    private fun prefixEndOf(text: String, cfg: CatConfig): Int {
        val prefix = existingPrefix(text, cfg) ?: return 0
        val start = text.indexOfFirst { !it.isWhitespace() }
        return start + prefix.length
    }

    /** 正文的起始下标（即开头前缀之后），光标映射用。 */
    fun contentStart(text: String, cfg: CatConfig): Int = prefixEndOf(text, cfg)

    /** [pickPrefix] 的逆操作：剥掉开头前缀。 */
    fun stripPrefix(text: String, cfg: CatConfig): String {
        val end = prefixEndOf(text, cfg)
        return if (end == 0) text else text.substring(end).trimStart()
    }

    /**
     * 把用户原文加工成最终文本。
     *
     * @param keepPrefix 当前输入框里已经存在的前缀。传入它可以让前缀在一条消息内保持不变
     * （引擎每次触发都会调用本方法，若每次都重新挑，前缀会随内容变化而跳变）。
     * 传 null 表示这条消息还没有前缀，需要新挑一个。
     */
    fun process(original: String, config: CatConfig, keepPrefix: String? = null): String {
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
        // 前缀最后加：放前面会被 addMeow 当成句首片段，可能被多插一个后缀。
        // 已有前缀就沿用，保证一条消息内前缀稳定。
        if (config.enablePrefix) {
            val prefix = keepPrefix?.takeIf { it.isNotEmpty() } ?: pickPrefix(text, config)
            if (prefix.isNotEmpty()) {
                text = "$prefix$text"
            }
        }
        return text
    }
}