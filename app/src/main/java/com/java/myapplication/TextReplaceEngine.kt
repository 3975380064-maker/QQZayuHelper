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
        private const val PLACEHOLDER = "\ue000BM\ue001"
        private const val ECHO_WINDOW_MS = 800L
        private const val ROOT_RETRY_DELAY_MS = 80L
        private const val ROOT_RETRY_MAX = 3
        private const val WATCHDOG_TIMEOUT_MS = 5000L
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

    private val watchdogTask = object : Runnable {
        override fun run() {
            if (processing) {
                val elapsed = System.currentTimeMillis() - processingStartTime
                if (elapsed >= WATCHDOG_TIMEOUT_MS) {
                    Log.w(TAG, "watchdog: processing 卡死 ${elapsed}ms，强制重置")
                    processing = false
                } else {
                    handler.postDelayed(this, WATCHDOG_TIMEOUT_MS - elapsed)
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
                    lastTextLength = 0
                    Log.d(TAG, "QQ 内部切换，保留状态")
                }
            }

            AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED -> {
                val cfg = loadConfig()
                val mode = cfg.processingMode

                val cs = event.text ?: return
                val currentText = cs.joinToString("")
                val currentLen = currentText.length
                if (currentLen < lastTextLength) {
                    lastTextLength = currentLen
                    return
                }
                lastTextLength = currentLen

                if (mode == CatConfig.REAL_TIME_MODE) {
                    handler.removeCallbacks(idleTask)
                    handler.postDelayed(idleTask, cfg.idleDelayMs.toLong())
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

    fun resetState() {
        processing = false
        userOriginal = ""
        lastSet = ""
        lastWriteTime = 0L
        lastTextLength = 0
        lastWrittenText = ""
    }

    fun onInterrupt() {
        processing = false
    }

    /** 核心处理流程 */
    fun doProcess() {
        if (processing) return
        processing = true
        processingStartTime = System.currentTimeMillis()
        handler.postDelayed(watchdogTask, WATCHDOG_TIMEOUT_MS)

        try {
            val cfg = loadConfig()
            if (!cfg.enabled) return

            val root = findRootWithRetry() ?: return
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

                    val isRealtime = cfg.processingMode == CatConfig.REAL_TIME_MODE

                    if (!isRealtime && lastSet.isEmpty()) {
                        userOriginal = stripAll(raw, cfg)
                    } else if (lastSet.isNotEmpty() && raw.startsWith(lastSet)) {
                        val added = raw.substring(lastSet.length)
                        userOriginal += added
                    } else if (lastSet.isEmpty()) {
                        userOriginal = stripAll(raw, cfg)
                    } else {
                        userOriginal = stripAll(raw, cfg)
                    }

                    if (userOriginal.isEmpty()) return

                    val target = TextProcessor.process(userOriginal, cfg)
                    if (target == raw) {
                        lastSet = target
                        return
                    }

                    Log.d(TAG, "写入: raw=$raw  userOriginal=$userOriginal  target=$target")

                    val cursorPos = computeCursorPos(userOriginal, cfg)
                    val ok = setTextOrFallback(inp, target, cursorPos)
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
        }
    }

    private fun findRootWithRetry(): AccessibilityNodeInfo? {
        var retries = 0
        while (retries < ROOT_RETRY_MAX) {
            val root = service.rootInActiveWindow
            if (root != null) return root
            retries++
            if (retries < ROOT_RETRY_MAX) {
                try { Thread.sleep(ROOT_RETRY_DELAY_MS) } catch (_: InterruptedException) { break }
            }
        }
        return service.rootInActiveWindow
    }

    private fun computeCursorPos(original: String, cfg: CatConfig): Int {
        var pos = original.length
        if (cfg.enableWoToBenmiao) {
            val woCount = original.count { it == '我' }
            pos += woCount * (cfg.woReplacement.length - 1)
        }
        if (cfg.enableNiToZhuren) {
            val niCount = original.count { it == '你' }
            pos += niCount * (cfg.niReplacement.length - 1)
        }
        return pos
    }

    private fun setTextOrFallback(node: AccessibilityNodeInfo, text: String, cursorPos: Int): Boolean {
        if (setTextBeforeMeow(node, text, cursorPos)) return true

        Log.w(TAG, "SET_TEXT 失败，尝试剪贴板粘贴 fallback")
        return try {
            val clipboard = service.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", text)
            clipboard.setPrimaryClip(clip)

            val clearBundle = Bundle()
            clearBundle.putCharSequence("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE", "")
            node.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, clearBundle)

            node.performAction(AccessibilityNodeInfo.ACTION_PASTE)

            clipboard.setPrimaryClip(ClipData.newPlainText("", ""))
            Log.d(TAG, "剪贴板粘贴 fallback 成功")
            true
        } catch (e: Exception) {
            Log.w(TAG, "剪贴板粘贴 fallback 也失败", e)
            false
        }
    }

    private fun setTextBeforeMeow(node: AccessibilityNodeInfo, text: String, cursorPos: Int): Boolean {
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
            Log.w(TAG, "setTextBeforeMeow 异常", e)
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
        return last in charArrayOf('。', '！', '!', '？', '?', ' ')
    }

    private fun stripAll(text: String, cfg: CatConfig): String {
        if (text.isNullOrEmpty()) return ""
        var result = text
        val emotes = cfg.getActiveEmoticons()
        val sorted = emotes.sortedByDescending { it.length }
        for (em in sorted) {
            if (em.isEmpty()) continue
            while (true) {
                val idx = result.indexOf(em)
                if (idx < 0) break
                var st = idx
                if (st > 0 && result[st - 1] == ' ') st -= 1
                result = result.substring(0, st) + result.substring(idx + em.length)
            }
        }
        result = result.replace(Regex("\\s*[\\p{S}\\p{So}\\p{Sm}\\p{Sk}\\p{P}]{3,}\\s*"), " ")
        result = result.replace(cfg.woReplacement, PLACEHOLDER)
        result = result.replace(cfg.meowSuffix, "")
        result = result.replace(PLACEHOLDER, cfg.woReplacement)
        return result.trim()
    }
}