package com.java.myapplication

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ReplaceRulesTest {

    private fun config() = CatConfig().apply {
        enableWoToBenmiao = true
        woReplacement = "本喵"
        enableNiToZhuren = false
        niReplacement = "主人"
        customRules = emptyArray()
    }

    @Test
    fun `我被替换成本喵`() {
        assertEquals("本喵要吃饭", ReplaceRules.apply("我要吃饭", config()))
    }

    @Test
    fun `关闭开关后不替换`() {
        val cfg = config().apply { enableWoToBenmiao = false }
        assertEquals("我要吃饭", ReplaceRules.apply("我要吃饭", cfg))
    }

    @Test
    fun `你按开关决定是否替换`() {
        val cfg = config().apply {
            enableNiToZhuren = true
            niReplacement = "主人"
        }
        assertEquals("主人好", ReplaceRules.apply("你好", cfg))
        assertEquals("你好", ReplaceRules.apply("你好", config()))
    }

    @Test
    fun `apply与revert互逆`() {
        val cfg = config().apply {
            enableNiToZhuren = true
            niReplacement = "主人"
        }
        // 注意：原文里不能包含替换后的词（如“本喵”），否则还原时无法区分
        // 它是用户手打的还是引擎替换出来的 —— 这是当前反向还原方式的已知局限。
        val original = "我要你陪吃饭"
        val processed = ReplaceRules.apply(original, cfg)
        assertEquals("本喵要主人陪吃饭", processed)
        assertEquals(original, ReplaceRules.revert(processed, cfg))
    }

    @Test
    fun `链式自定义规则可正确还原`() {
        val cfg = config().apply {
            enableWoToBenmiao = false
            customRules = arrayOf("说=曰", "曰=云")
        }
        // 链式替换：说 -> 曰 -> 云
        assertEquals("云话", ReplaceRules.apply("说话", cfg))
        // 逆序还原应回到原文
        assertEquals("说话", ReplaceRules.revert("云话", cfg))
    }

    @Test
    fun `非法规则被忽略`() {
        assertNull(ReplaceRules.parseRule("没有等号"))
        assertNull(ReplaceRules.parseRule("=只有替换词"))
        assertNull(ReplaceRules.parseRule("只有原词="))
        assertEquals("ab", ReplaceRules.parseRule("a=b").let { it!!.first + it.second })
    }

    @Test
    fun `非法规则不影响替换结果`() {
        val cfg = config().apply {
            customRules = arrayOf("没有等号", "说=曰")
        }
        assertEquals("曰", ReplaceRules.apply("说", cfg))
    }

    @Test
    fun `空串处理不崩溃`() {
        assertEquals("", ReplaceRules.apply("", config()))
        assertEquals("", ReplaceRules.revert("", config()))
    }
}