package com.java.myapplication

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
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
    fun `自定义规则生效`() {
        val cfg = config().apply {
            enableWoToBenmiao = false
            customRules = arrayOf("说=曰")
        }
        assertEquals("曰话喵", TextProcessor.process("说话", cfg))
    }
}