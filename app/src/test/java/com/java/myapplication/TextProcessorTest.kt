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
    fun `自定义规则生效`() {
        val cfg = config().apply {
            enableWoToBenmiao = false
            customRules = arrayOf("说=曰")
        }
        assertEquals("曰话喵", TextProcessor.process("说话", cfg))
    }
}