package com.java.myapplication

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class TextProcessorTest {

    private fun config() = CatConfig().apply {
        enableWoToBenmiao = true
        woReplacement = "本喵"
        enableNiToZhuren = false
        enableMeow = true
        meowSuffix = "喵"
        enableRandomEmoticon = false
        customEmoticons = emptyArray()
        customRules = emptyArray()
    }

    @Test
    fun `替换加后缀`() {
        assertEquals("本喵要吃饭喵", TextProcessor.process("我要吃饭", config()))
    }

    @Test
    fun `末尾已有后缀不重复追加`() {
        val cfg = config().apply { enableWoToBenmiao = false }
        assertEquals("吃饭喵", TextProcessor.process("吃饭喵", cfg))
    }

    @Test
    fun `加入颜文字且结果稳定`() {
        val cfg = config().apply { enableRandomEmoticon = true }
        val first = TextProcessor.process("我要吃饭", cfg)
        val second = TextProcessor.process("我要吃饭", cfg)
        assertEquals(first, second)
        assertTrue(first.startsWith("本喵要吃饭喵"))
        assertTrue(first.length > "本喵要吃饭喵".length)
    }

    @Test
    fun `关闭所有开关时原样返回`() {
        val cfg = config().apply {
            enableWoToBenmiao = false
            enableMeow = false
            enableRandomEmoticon = false
        }
        assertEquals("我要吃饭", TextProcessor.process("我要吃饭", cfg))
    }

    @Test
    fun `空白输入原样返回`() {
        assertEquals("   ", TextProcessor.process("   ", config()))
    }

    @Test
    fun `句中标点分段都会被加上后缀`() {
        val cfg = config().apply { enableWoToBenmiao = false }
        val result = TextProcessor.process("你好，吃饭", cfg)
        assertTrue(result.startsWith("你好喵"))
        assertFalse(result.contains("，，"))
    }

    @Test
    fun `后缀剥离是加后缀的逆操作`() {
        val cfg = config()
        val processed = TextProcessor.process("你好，吃饭", cfg)
        assertEquals("你好喵，吃饭喵", processed)
        // 必须把每一段的后缀都剥掉，只剥末尾会导致再次写入时后缀累积
        assertEquals("你好，吃饭", TextProcessor.stripMeowSuffix(processed, cfg))
    }

    @Test
    fun `关掉后缀时不剥离`() {
        val cfg = config().apply { enableMeow = false }
        assertEquals("你好喵，吃饭喵", TextProcessor.stripMeowSuffix("你好喵，吃饭喵", cfg))
    }

    @Test
    fun `contentEnd跳过末尾装饰`() {
        val cfg = config().apply { enableRandomEmoticon = true }
        val processed = TextProcessor.process("你好，吃饭", cfg)
        val content = "你好喵，吃饭"
        assertTrue(processed.startsWith(content))
        // 末尾的后缀与颜文字都应被跳过，避免光标映射切出半个颜文字
        assertEquals(content.length, TextProcessor.contentEnd(processed, cfg))
    }

    @Test
    fun `没有装饰时contentEnd就是文本长度`() {
        val cfg = config().apply {
            enableMeow = false
            enableRandomEmoticon = false
        }
        val text = "你好，吃饭"
        assertEquals(text.length, TextProcessor.contentEnd(text, cfg))
    }

    @Test
    fun `开启前缀后会加上前缀且可剥离`() {
        val cfg = config().apply { enablePrefix = true }
        val out = TextProcessor.process("我要吃饭", cfg)
        assertTrue(out.endsWith("本喵要吃饭喵"))
        assertTrue(out.length > "本喵要吃饭喵".length)
        assertEquals("本喵要吃饭喵", TextProcessor.stripPrefix(out, cfg))
    }

    @Test
    fun `关闭前缀时不加也不剥`() {
        val out = TextProcessor.process("我要吃饭", config())
        assertEquals("本喵要吃饭喵", out)
        assertEquals("唔…本喵要吃饭喵", TextProcessor.stripPrefix("唔…本喵要吃饭喵", config()))
    }

    @Test
    fun `keepPrefix会沿用已有前缀`() {
        val cfg = config().apply { enablePrefix = true }
        val first = TextProcessor.process("我要吃饭", cfg)
        val prefix = TextProcessor.existingPrefix(first, cfg)
        assertNotNull(prefix)
        // 内容变化后仍沿用同一个前缀，否则用户续写时前缀会跳变
        val second = TextProcessor.process("我要吃饭哈", cfg, prefix)
        assertEquals(prefix, TextProcessor.existingPrefix(second, cfg))
        assertTrue(second.endsWith("本喵要吃饭哈喵"))
    }

    @Test
    fun `前缀选择是确定性的`() {
        val cfg = config().apply { enablePrefix = true }
        assertEquals(TextProcessor.process("我要吃饭", cfg), TextProcessor.process("我要吃饭", cfg))
    }

    @Test
    fun `existingPrefix与contentStart一致`() {
        val cfg = config().apply { enablePrefix = true }
        val out = TextProcessor.process("我要吃饭", cfg)
        val prefix = TextProcessor.existingPrefix(out, cfg)!!
        assertEquals(prefix.length, TextProcessor.contentStart(out, cfg))
        // 没有前缀时 contentStart 为 0
        assertEquals(0, TextProcessor.contentStart("我要吃饭", cfg))
    }

    @Test
    fun `raw没有装饰时不算只差装饰`() {
        // 回归用例：用户刚打完纯文本（无任何装饰），引擎要补前缀+后缀，
        // 不能被“只差装饰”判定拦下，否则表现就是完全没反应。
        val cfg = config().apply { enablePrefix = true }
        val raw = "1236。？"
        val target = TextProcessor.process(raw, cfg)
        assertNotNull(TextProcessor.existingPrefix(target, cfg))
        assertFalse(TextProcessor.isDecorationOnlyDiff(raw, target, cfg))
    }

    @Test
    fun `raw已有装饰时只差颜文字才算`() {
        val cfg = config().apply {
            enablePrefix = true
            prefixText = "唔…"
            enableRandomEmoticon = true
        }
        val emoticons = cfg.getActiveEmoticons()
        // 正文、前缀、后缀都一样，只有末尾颜文字不同 —— 这才是应当跳过写入的情况
        val raw = "唔…本喵要吃饭喵 ${emoticons[0]}"
        val target = "唔…本喵要吃饭喵 ${emoticons[1]}"
        assertTrue(TextProcessor.isDecorationOnlyDiff(raw, target, cfg))
    }

    @Test
    fun `关闭颜文字时不会把用户正文当成颜文字剥掉`() {
        // 回归用例：功能关着时引擎不会加颜文字，剥离也必须跟着关，
        // 否则用户自己打的、恰好长得像内置颜文字的正文会被误当成装饰。
        val cfg = config().apply { enableRandomEmoticon = false }
        val em = CatConfig.BUILTIN_EMOTICONS[0]
        val text = "我要吃饭 $em"
        assertEquals(text, TextProcessor.stripSuffixEmoticon(text, cfg))
    }

    @Test
    fun `开启颜文字时会剥掉末尾颜文字`() {
        val cfg = config().apply { enableRandomEmoticon = true }
        val em = CatConfig.BUILTIN_EMOTICONS[0]
        assertEquals("我要吃饭", TextProcessor.stripSuffixEmoticon("我要吃饭 $em", cfg))
    }

    @Test
    fun `自定义规则生效`() {
        val cfg = config().apply {
            enableWoToBenmiao = false
            customRules = arrayOf("说=曰")
        }
        assertEquals("曰话喵", TextProcessor.process("说话", cfg))
    }
}