package com.java.myapplication

import android.content.Context

class CatConfig {
    var enabled: Boolean = true
    var enableMeow: Boolean = true
    var enableWoToBenmiao: Boolean = true
    var enableNiToZhuren: Boolean = false
    var enableRandomEmoticon: Boolean = true
    var customEmoticons: Array<String> = emptyArray()
    var processingMode: String = PUNCTUATION_MODE
    var woReplacement: String = "本喵"
    var niReplacement: String = "主人"
    var meowSuffix: String = "喵"
    var idleDelayMs: Int = 1000
    var customRules: Array<String> = emptyArray()  // 每项格式 "原词=替换词"

    companion object {
        private const val PREFS_NAME = "cat_config"
        private const val KEY_ENABLED = "enabled"
        private const val KEY_ENABLE_MEOW = "enable_meow"
        private const val KEY_ENABLE_WO = "enable_wo"
        private const val KEY_ENABLE_NI = "enable_ni"
        private const val KEY_ENABLE_EMOTICON = "enable_emoticon"
        private const val KEY_CUSTOM_EMOTICONS = "custom_emoticons"
        private const val KEY_PROCESSING_MODE = "processing_mode"
        private const val KEY_WO_REPLACEMENT = "wo_replacement"
        private const val KEY_NI_REPLACEMENT = "ni_replacement"
        private const val KEY_MEOW_SUFFIX = "meow_suffix"
        private const val KEY_IDLE_DELAY = "idle_delay"
        private const val KEY_CUSTOM_RULES = "custom_rules"

        const val REAL_TIME_MODE = "real_time"
        const val PUNCTUATION_MODE = "punctuation"

        val BUILTIN_EMOTICONS = arrayOf(
            "=^•ω•^=", "(=^･ω･^=)", "(>^ω^<)", "(=^.^=)", "(=^x^=)", "(=´ｪ`)", "≡ω≡",
            "／(=･ｘ･=)＼", "(=(∞)=)", "(=⌒‿⌒=)", "=^_^=", "=. .=", "=；ω；=",
            "( =①ω①= )", "(≧^.^≦)~", "(>^.^<)", "(=^･^=)∫", "(✧ω✧)", "ฅ(^･ω･^)ฅ",
            "\\(=^･^=)/", "=^･ω･^=", "~( =^･^)~", "(｀へ´*)", "(=･ｪ･=)`", "(｡=ˇ‸ˇ=｡)",
            "(´;ω;｀)", "(｡•́︿•̀｡)", "(´･_･)`", "~(=^･^)", "(∪｡∪)｡｡｡zzz", "(=^･^=)y",
            "₍(=^･ω･^=)₎", "(ฅ´ωฅ)`", "/ᐠ｡ꞈ｡ᐟ\\", "ฅ^•ﻌ•^ฅ", "(=ｘェｘ=)",
            "( =ω= )..", "(=￣ω￣=;)", "(^･ｪ･^)", "(=´∇｀=)",
            "／(=´x)=＼`", "( =°ω°= ) ？", "( =＾ω＾= ) ～♪", "(=◕ω◕=)", "(=°ω°=)",
            "(*°ω°*ฅ)", "^ ̳ට ̫ ට ̳^", "^⌯𖥦⌯^ ੭ ^", "(๑•̀ω•́ฅ)", "ฅ(*`ω´*)ฅ",
            "ฅ●ω●ฅ", "ฅ( ̳• ◡ • ̳)ฅ", "/ᐠ - ˕ -マ Ⳋ", "(^ω^ฅ)", "₍⸍⸌·͈༝·͈⸍⸌₎◞",
            "(๑•̀ω•́ฅ)", "^•͈༝•^ฅ", "₍^ >ヮ<^₎", "ฅ•̀∀•́ฅ", "!!^⌯𖥦⌯^ ੭!!",
            "^ ̳ᴗ  ̫ ᴗ ̳^", "₍^⸝⸝> ·̫ <⸝⸝ ^₎", "(ฅ◑ω◑ฅ)", "ヾ((๑˘ㅂ˘๑)ฅ",
            "(^•ᴥ•^)", "ฅ(≧▽≦)ฅ", "(=ↀωↀ=)", "(`･ω･´)ฅ", "ฅ(=´▽`=)ฅ",
            "ฅ ̳͒•ˑ̫• ̳͒ฅ♡", "ヽ(ฅ≧へ≦)ฅ", "(ฅ´ω`ฅ)", "ฅ꒰ ⸝˶• •˶⸝꒱ฅ",
            "ฅ^._.^ฅ", "ฅ՞•ﻌ•՞ฅ", "⌯•ㅅ•⌯", "(ฅ>ω<*ฅ)", "ฅ^-﹃-^ฅ",
            "ฅ(*°ω°*ฅ)", "ฅ^••^ฅ",
            "៸៸᳐៸᳐>⩊<៸៸᳐៸᳐", "ヾ(=･ω･=)o", "(=｀ω´=)", ">𖥦O",
            "ᯠ>△<ᯄ", "ᯠ>ω<ᯄ", "ᯠ  .   ̫  .  ᯄ੭", "ฅ/ᐠ .⸝⸝⸝. ྀིﾏฅ",
            "ᯠ  _   ̫  _  ᯄ ੭", "ᯠ  _   ̫  _ ̥ ᯄ ੭", "＞  ̫ O",
            "ᯠ  ·  v  ·  ᯄ ੭", "ᯠ  Q   ̫  Q  ᯄ ੭", "⸝⸝⸝ ╸▵╺⸝⸝⸝",
            " ꉂ ᳐˶ᵒ ᵕ ˂˶ ᳐ฅ", "₍^˶ ╸𖥦  ╸˵^₎⟆"
        )

        fun load(context: Context): CatConfig {
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val config = CatConfig()
            config.enabled = prefs.getBoolean(KEY_ENABLED, true)
            config.enableMeow = prefs.getBoolean(KEY_ENABLE_MEOW, true)
            config.enableWoToBenmiao = prefs.getBoolean(KEY_ENABLE_WO, true)
            config.enableNiToZhuren = prefs.getBoolean(KEY_ENABLE_NI, false)
            config.enableRandomEmoticon = prefs.getBoolean(KEY_ENABLE_EMOTICON, true)
            config.processingMode = prefs.getString(KEY_PROCESSING_MODE, PUNCTUATION_MODE) ?: PUNCTUATION_MODE
            config.woReplacement = prefs.getString(KEY_WO_REPLACEMENT, "本喵") ?: "本喵"
            config.niReplacement = prefs.getString(KEY_NI_REPLACEMENT, "主人") ?: "主人"
            config.meowSuffix = prefs.getString(KEY_MEOW_SUFFIX, "喵") ?: "喵"
            config.idleDelayMs = prefs.getInt(KEY_IDLE_DELAY, 1000)

            val raw = prefs.getString(KEY_CUSTOM_EMOTICONS, "")
            config.customEmoticons = if (raw.isNullOrEmpty()) {
                emptyArray()
            } else {
                raw.split("\n").filter { it.isNotBlank() }.toTypedArray()
            }

            val rulesRaw = prefs.getString(KEY_CUSTOM_RULES, "")
            config.customRules = if (rulesRaw.isNullOrEmpty()) {
                emptyArray()
            } else {
                rulesRaw.split("\n").filter { it.isNotBlank() }.toTypedArray()
            }
            return config
        }

        fun save(context: Context, config: CatConfig) {
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            prefs.edit()
                .putBoolean(KEY_ENABLED, config.enabled)
                .putBoolean(KEY_ENABLE_MEOW, config.enableMeow)
                .putBoolean(KEY_ENABLE_WO, config.enableWoToBenmiao)
                .putBoolean(KEY_ENABLE_NI, config.enableNiToZhuren)
                .putBoolean(KEY_ENABLE_EMOTICON, config.enableRandomEmoticon)
                .putString(KEY_PROCESSING_MODE, config.processingMode)
                .putString(KEY_WO_REPLACEMENT, config.woReplacement)
                .putString(KEY_NI_REPLACEMENT, config.niReplacement)
                .putString(KEY_MEOW_SUFFIX, config.meowSuffix)
                .putInt(KEY_IDLE_DELAY, config.idleDelayMs)
                .putString(KEY_CUSTOM_EMOTICONS, config.customEmoticons.joinToString("\n"))
                .putString(KEY_CUSTOM_RULES, config.customRules.joinToString("\n"))
                .apply()
        }
    }

    fun getActiveEmoticons(): Array<String> {
        return if (customEmoticons.isNotEmpty()) customEmoticons else BUILTIN_EMOTICONS
    }
}