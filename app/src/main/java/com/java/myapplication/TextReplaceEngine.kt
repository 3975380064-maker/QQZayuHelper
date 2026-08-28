package com.java.myapplication

import android.accessibilityservice.AccessibilityService
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

/**
 * 文本替换核心引擎。
 * 借鉴 AutoTask 的分离思想，将业务逻辑从 AccessibilityService 中抽离。
 * 单一职责：接收事件、查找输入框、执行替换、管理状态。
 */
class TextReplaceEngine(private val service: AccessibilityService) {

    companion object {
        private const val TAG = "QQCatSvc"
        private const val ID_INPUT = "com.tencent.mobileqq:id/input"
        private const val PLACEHOLDER_WO = "\ue000"
        private const val PLACEHOLDER_NI = "\ue001"
        private const val CUSTOM_RULE_PLACEHOLDER_PREFIX = "\ue010"
        private const val ECHO_WINDOW_MS = 800L
    }

    var userOriginal = ""
    var lastSet = ""
    var processing = false
    var lastWriteTime = 0L
    var lastTextLength = 0
    var lastWrittenText = ""
    var currentPkg = ""

    private val handler = Handler(Looper.getMainLooper())
    private val idleTask = Runnable { doProcess() }
    private var processingStartTime = 0L
    private var hasPendingIdleTask = false

    private val watchdogTask = object : Runnable {
        override fun run() {
            if (processing) {
                val elapsed = System.currentTimeMillis() - processingStartTime
                if (elapsed >= 5000L) {
                    Log.w(TAG, "watchdog: processing 卡死 ${elapsed}ms，强制重置")
                    processing = false
                } else {
                    handler.postDelayed(this, 5000L - elapsed)
                }
            }
        }
    }

    private fun loadConfig(): CatConfig = CatConfig.load(service)

    /** 事件入口：由 Service 在 onAccessibilityEvent 中调用 */
    fun onEvent(event: AccessibilityEvent) {
        val pkg = event.packageName?.toString() ?: ""
        if (pkg != "com.tencent.mobileqq" && pkg != "com.tencent.mobileqqi") return

        when (event.eventType) {
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {
                if (pkg != currentPkg) {
                    resetState()
                    currentPkg = pkg
                    Log.d(TAG, "包名变化，重置状态: $pkg")
                } else {
                    // QQ 内切换聊天窗口 —— 重置 lastSet 避免误匹配
                    lastTextLength = 0
                    lastSet = ""
                    userOriginal = ""
                    lastWrittenText = ""
                    Log.d(TAG, "QQ 内部切换，重置 lastSet")
                }
            }

            AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED -> {
                val cfg = loadConfig()
                val mode = cfg.processingMode

                val cs = event.text ?: return
                val currentText = cs.joinToString("")
                val currentLen = currentText.length
                if (currentLen < lastTextLength) {
                    // 删除文本：取消 pending idleTask，更新长度
                    lastTextLength = currentLen
                    cancelPendingIdleTask()
                    return
                }
                lastTextLength = currentLen

                if (mode == CatConfig.REAL_TIME_MODE) {
                    scheduleIdleTask(cfg.idleDelayMs.toLong())
                } else {
                    val raw = currentText.trim()
                    if (raw.isNotEmpty() && isPunctuationEnding(raw)) {
                        Log.d(TAG, "标点触发: $raw")
                        doProcess()
                    }
                }
            }
        }
    }

    private fun cancelPendingIdleTask() {
        if (hasPendingIdleTask) {
            handler.removeCallbacks(idleTask)
            hasPendingIdleTask = false
        }
    }

    private fun scheduleIdleTask(delayMs: Long) {
        cancelPendingIdleTask()
        handler.postDelayed(idleTask, delayMs)
        hasPendingIdleTask = true
    }

    fun resetState() {
        processing = false
        cancelPendingIdleTask()
        userOriginal = ""
        lastSet = ""
        lastWriteTime = 0L
        lastTextLength = 0
        lastWrittenText = ""
    }

    fun onInterrupt() {
        processing = false
        cancelPendingIdleTask()
    }

    /** 核心处理流程 */
    fun doProcess() {
        if (processing) return
        processing = true
        processingStartTime = System.currentTimeMillis()
        handler.postDelayed(watchdogTask, 5000L)

        try {
            val cfg = loadConfig()
            if (!cfg.enabled) return

            val root = service.rootInActiveWindow ?: return
            try {
                val inp = findNodeById(root, ID_INPUT) ?: return
                try {
                    val cs = inp.text
                    if (cs == null || cs.isEmpty()) {
                        userOriginal = ""
                        lastSet = ""
                        return
                    }

                    val rawStr = cs.toString()
                    if (rawStr.contains('@')) {
                        Log.d(TAG, "检测到 @，跳过处理")
                        return
                    }

                    val raw = rawStr.trim()
                    if (raw.isEmpty()) {
                        userOriginal = ""
                        lastSet = ""
                        return
                    }

                    val now = System.currentTimeMillis()

                    // 回显判定
                    if (lastWriteTime > 0 && now - lastWriteTime < ECHO_WINDOW_MS) {
                        if (raw == lastWrittenText) {
                            Log.d(TAG, "回显跳过（内容匹配）: $raw")
                            lastWriteTime = 0
                            return
                        }
                    }

                    // 更新 userOriginal
                    if (lastSet.isNotEmpty() && raw.startsWith(lastSet)) {
                        // 用户在末尾继续输入
                        val added = raw.substring(lastSet.length)
                        userOriginal += added
                    } else {
                        // 文本不匹配预期（用户修改/删除/切换窗口），重新剥离
                        userOriginal = stripEngineOutput(raw, cfg)
                    }

                    if (userOriginal.isEmpty()) return

                    val target = TextProcessor.process(userOriginal, cfg)
                    if (target == raw) {
                        lastSet = target
                        return
                    }

                    Log.d(TAG, "写入: raw=$raw  userOriginal=$userOriginal  target=$target")

                    // 根据用户实际光标位置映射到 target 中的对应位置
                    val cursorInTarget = mapCursorPosition(raw, inp.textSelectionStart, target, cfg)
                    val ok = setTextOrFallback(inp, target, cursorInTarget)
                    if (ok) {
                        lastSet = target
                        lastWrittenText = target
                        lastWriteTime = System.currentTimeMillis()
                    }
                } finally {
                    inp.recycle()
                }
            } finally {
                root.recycle()
            }
        } finally {
            processing = false
            handler.removeCallbacks(watchdogTask)
            hasPendingIdleTask = false
        }
    }

    /**
     * 逆向剥离引擎输出的附加内容，还原用户原始输入。
     * 只剥离引擎已知追加的部分（句尾后缀、表情、替换词），不做全局替换。
     */
    private fun stripEngineOutput(text: String, cfg: CatConfig): String {
        var result = text

        // 1. 剥离句尾表情（只从末尾剥离一次，带空格情况）
        if (cfg.enableRandomEmoticon) {
            val emotes = cfg.getActiveEmoticons().sortedByDescending { it.length }
            for (em in emotes) {
                if (result.endsWith(" $em")) {
                    result = result.substring(0, result.length - em.length - 1).trim()
                    break
                }
                if (result.endsWith(em)) {
                    result = result.substring(0, result.length - em.length).trim()
                    break
                }
            }
        }

        // 2. 剥离句尾后缀（只从末尾剥离一次）
        if (cfg.enableMeow && result.endsWith(cfg.meowSuffix)) {
            result = result.substring(0, result.length - cfg.meowSuffix.length).trim()
        }

        // 3. 反转自定义替换规则（用唯一占位符避免冲突）
        // 例：说=曰 和 话=曰 都映射到「曰」，逆序时先用唯一占位符区分再还原
        if (cfg.customRules.isNotEmpty()) {
            val reversePlaceholders = mutableMapOf<String, String>()
            cfg.customRules.forEachIndexed { index, rule ->
                val parts = rule.split("=", limit = 2)
                if (parts.size == 2 && parts[0].isNotBlank()) {
                    val ph = CUSTOM_RULE_PLACEHOLDER_PREFIX + index.toChar()
                    reversePlaceholders[ph] = parts[0]
                    result = result.replace(parts[1], ph)
                }
            }
            for ((ph, original) in reversePlaceholders) {
                result = result.replace(ph, original)
            }
        }

        // 4. 反转替换词（用占位符避免交叉污染）
        if (cfg.enableWoToBenmiao && cfg.woReplacement.isNotEmpty()) {
            result = result.replace(cfg.woReplacement, PLACEHOLDER_WO)
        }
        if (cfg.enableNiToZhuren && cfg.niReplacement.isNotEmpty()) {
            result = result.replace(cfg.niReplacement, PLACEHOLDER_NI)
        }
        result = result.replace(PLACEHOLDER_WO, "我")
        result = result.replace(PLACEHOLDER_NI, "你")

        return result.trim()
    }

    /**
     * 根据用户在 raw 中的光标位置，映射到 target 中的对应位置。
     * 只计算替换变换（我/你/自定义规则），不计后缀和颜文字，
     * 避免光标跳到末尾。
     */
    private fun mapCursorPosition(raw: String, cursorPos: Int, target: String, cfg: CatConfig): Int {
        if (cursorPos <= 0 || cursorPos >= raw.length) return target.length
        val rawPrefix = raw.substring(0, cursorPos)
        val userPrefix = stripEngineOutput(rawPrefix, cfg)
        if (userPrefix.isEmpty()) return target.length
        // 只应用替换变换，不加后缀/颜文字
        val mapped = applyReplacementsOnly(userPrefix, cfg)
        return mapped.length.coerceIn(0, target.length)
    }

    /** 只应用替换变换（我/你/自定义规则），不加后缀/颜文字 */
    private fun applyReplacementsOnly(text: String, cfg: CatConfig): String {
        var result = text
        if (cfg.enableWoToBenmiao && cfg.woReplacement.isNotEmpty()) {
            result = result.replace("我", cfg.woReplacement)
        }
        if (cfg.enableNiToZhuren && cfg.niReplacement.isNotEmpty()) {
            result = result.replace("你", cfg.niReplacement)
        }
        for (rule in cfg.customRules) {
            val parts = rule.split("=", limit = 2)
            if (parts.size == 2 && parts[0].isNotBlank()) {
                result = result.replace(parts[0], parts[1])
            }
        }
        return result
    }

    /**
     * 设置文本或剪贴板 fallback。
     * 根据光标位置映射，用户在前面/中间输入时光标不乱跳。
     */
    private fun setTextOrFallback(node: AccessibilityNodeInfo, text: String, cursorPos: Int): Boolean {
        if (setTextWithSelection(node, text, cursorPos)) return true

        Log.w(TAG, "SET_TEXT 失败，尝试剪贴板粘贴 fallback")
        return try {
            val clipboard = service.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            // 保存用户原剪贴板内容
            val originalClip = clipboard.primaryClip

            val clip = ClipData.newPlainText("label", text)
            clipboard.setPrimaryClip(clip)

            val clearBundle = Bundle()
            clearBundle.putCharSequence("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE", "")
            node.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, clearBundle)

            node.performAction(AccessibilityNodeInfo.ACTION_PASTE)

            // 恢复用户剪贴板
            if (originalClip != null) {
                clipboard.setPrimaryClip(originalClip)
            } else {
                clipboard.setPrimaryClip(ClipData.newPlainText("", ""))
            }
            Log.d(TAG, "剪贴板粘贴 fallback 成功")
            true
        } catch (e: Exception) {
            Log.w(TAG, "剪贴板粘贴 fallback 也失败", e)
            false
        }
    }

    private fun setTextWithSelection(node: AccessibilityNodeInfo, text: String, cursorPos: Int): Boolean {
        if (node == null) return false
        return try {
            val b = Bundle()
            b.putCharSequence("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE", text)
            val ok = node.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, b)
            if (ok) {
                val a = Bundle()
                a.putInt("ACTION_ARGUMENT_SELECTION_START_INT", cursorPos)
                a.putInt("ACTION_ARGUMENT_SELECTION_END_INT", cursorPos)
                node.performAction(AccessibilityNodeInfo.ACTION_SET_SELECTION, a)
            }
            ok
        } catch (e: Exception) {
            Log.w(TAG, "setTextWithSelection 异常", e)
            false
        }
    }

    private fun findNodeById(n: AccessibilityNodeInfo?, id: String): AccessibilityNodeInfo? {
        if (n == null || id == null) return null
        if (id == n.viewIdResourceName) {
            return AccessibilityNodeInfo.obtain(n)
        }
        for (i in 0 until n.childCount) {
            val c = n.getChild(i) ?: continue
            val r = findNodeById(c, id)
            c.recycle()
            if (r != null) return r
        }
        return null
    }

    private fun isPunctuationEnding(s: String): Boolean {
        if (s.isNullOrEmpty()) return false
        val last = s.last()
        return last in charArrayOf('。', '！', '!', '？', '?')
    }
}