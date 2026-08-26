package com.java.myapplication

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PowerManager
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class QQAccessibilityService : AccessibilityService() {

    companion object {
        private const val TAG = "QQCatSvc"
        private const val ID_INPUT = "com.tencent.mobileqq:id/input"
        private const val PKG_QQ = "com.tencent.mobileqq"
        private const val PKG_QQI = "com.tencent.mobileqqi"
        private const val PLACEHOLDER = "\ue000BM\ue001"
        /** 回显跳过窗口，仅作为辅助判据 */
        private const val ECHO_WINDOW_MS = 800L
        /** root 重试间隔 */
        private const val ROOT_RETRY_DELAY_MS = 80L
        /** root 重试次数 */
        private const val ROOT_RETRY_MAX = 3
        /** processing watchdog 超时（5秒） */
        private const val WATCHDOG_TIMEOUT_MS = 5000L
    }

    private var userOriginal = ""
    private var lastSet = ""
    private var processing = false
    private var lastWriteTime = 0L
    private var lastTextLength = 0
    /** 上次写入的完整文本，用于内容匹配回显判定 */
    private var lastWrittenText = ""
    private var wakeLock: PowerManager.WakeLock? = null
    /** 当前所在包名，用于 WINDOW_STATE_CHANGED 判断是否切到其他应用 */
    private var currentPkg = ""
    /** watchdog 计时开始时间 */
    private var processingStartTime = 0L

    private val handler = Handler(Looper.getMainLooper())
    private val idleTask = Runnable { doProcess() }
    /** watchdog 任务：processing 卡死超时强制重置 */
    private val watchdogTask = object : Runnable {
        override fun run() {
            if (processing) {
                val elapsed = System.currentTimeMillis() - processingStartTime
                if (elapsed >= WATCHDOG_TIMEOUT_MS) {
                    Log.w(TAG, "watchdog: processing 卡死 ${elapsed}ms，强制重置")
                    processing = false
                } else {
                    // 还没超时，再等
                    handler.postDelayed(this, WATCHDOG_TIMEOUT_MS - elapsed)
                }
            }
        }
    }

    private fun loadConfig(): CatConfig = CatConfig.load(this)

    override fun onServiceConnected() {
        super.onServiceConnected()
        val info = AccessibilityServiceInfo()
        info.eventTypes = AccessibilityEvent.TYPE_VIEW_CLICKED or
                AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED or
                AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
        info.flags = AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS or
                AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS
        // 100ms 是社区常用值，兼顾实时性和事件去重
        info.notificationTimeout = 100
        info.packageNames = arrayOf(PKG_QQ, PKG_QQI)
        serviceInfo = info

        // 获取唤醒锁
        val pm = getSystemService(POWER_SERVICE) as PowerManager
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "QQCatSvc:WakeLock")
        wakeLock?.acquire()
        Log.d(TAG, "唤醒锁已获取")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        wakeLock?.let {
            if (it.isHeld) it.release()
        }
        wakeLock = null
        Log.d(TAG, "唤醒锁已释放")
        return super.onUnbind(intent)
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return

        val pkg = event.packageName?.toString() ?: ""
        if (pkg != PKG_QQ && pkg != PKG_QQI) return

        when (event.eventType) {
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {
                // 只在包名变化（切到别的 app 再回来）时重置全部状态
                // QQ 内部聊天切换不重置，保留用户输入状态
                if (pkg != currentPkg) {
                    processing = false
                    userOriginal = ""
                    lastSet = ""
                    lastWriteTime = 0L
                    lastTextLength = 0
                    lastWrittenText = ""
                    currentPkg = pkg
                    Log.d(TAG, "包名变化，重置状态: $pkg")
                } else {
                    // QQ 内部切换，只重置长度记录
                    lastTextLength = 0
                    Log.d(TAG, "QQ 内部切换，保留状态")
                }
            }

            AccessibilityEvent.TYPE_VIEW_CLICKED -> {
                val src = event.source ?: return
                src.recycle()
            }

            AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED -> {
                val cfg = loadConfig()
                val mode = cfg.processingMode

                val cs = event.text ?: return
                // event.text 是 List<CharSequence>，需拼接成纯文本
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
                    // 标点模式：判断结尾是否为标点，用纯文本判断
                    val raw = currentText.trim()
                    if (raw.isNotEmpty() && isPunctuationEnding(raw)) {
                        Log.d(TAG, "标点触发: $raw")
                        doProcess()
                    }
                }
            }
        }
    }

    override fun onInterrupt() {
        processing = false
    }

    /**
     * 事件驱动重试：先订阅 WINDOW_CONTENT_CHANGED，再取 root
     * 比固定 sleep 更可靠
     */
    private fun findRootWithRetry(): AccessibilityNodeInfo? {
        var retries = 0
        while (retries < ROOT_RETRY_MAX) {
            val root = rootInActiveWindow
            if (root != null) return root
            retries++
            if (retries < ROOT_RETRY_MAX) {
                try { Thread.sleep(ROOT_RETRY_DELAY_MS) } catch (_: InterruptedException) { break }
            }
        }
        // 最后一次尝试
        return rootInActiveWindow
    }

    /**
     * 查找输入框，多级 fallback，参考成熟方案（Open-AutoGLM 等）
     * 优先级：ID → className(EditText) → hintText → 可聚焦且可编辑的节点
     */
    private fun findInputNode(root: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        // 1. 按已知 ID
        findNodeById(root, ID_INPUT)?.let { return it }
        // 2. 按类名
        findNodeByClassName(root, "android.widget.EditText")?.let { return it }
        // 3. 按 hint 文本
        findNodeByHint(root, "说点什么")?.let { return it }
        findNodeByHint(root, "输入")?.let { return it }
        // 4. 按 isEditable
        findEditable(root)?.let { return it }
        // 5. 可聚焦且可编辑
        findFocusableEditable(root)?.let { return it }
        return null
    }

    fun doProcess() {
        if (processing) return
        processing = true
        processingStartTime = System.currentTimeMillis()
        // 启动 watchdog
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

                    // @mention 检测
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

                    // ===== 回显判定：内容匹配为主，时间为辅 =====
                    // 如果当前文本与上次写入的文本完全一致，且在上次写入后的短时间内，判定为回显
                    if (lastWriteTime > 0 && now - lastWriteTime < ECHO_WINDOW_MS) {
                        if (raw == lastWrittenText) {
                            // 内容完全匹配 → 回显，跳过
                            Log.d(TAG, "回显跳过（内容匹配）: $raw")
                            lastWriteTime = 0
                            return
                        }
                        // 内容不完全匹配，但时间窗口内 → 可能是用户输入，不跳过
                        // 但也不更新 lastSet，避免误判
                    }

                    val isRealtime = cfg.processingMode == CatConfig.REAL_TIME_MODE

                    // 构建 userOriginal
                    if (!isRealtime && lastSet.isEmpty()) {
                        userOriginal = stripAll(raw, cfg)
                        Log.d(TAG, "标点首次剥离: $userOriginal")
                    } else if (lastSet.isNotEmpty() && raw.startsWith(lastSet)) {
                        val added = raw.substring(lastSet.length)
                        userOriginal += added
                        Log.d(TAG, "前缀增量: +$added  userOriginal=$userOriginal")
                    } else if (lastSet.isEmpty()) {
                        userOriginal = stripAll(raw, cfg)
                        Log.d(TAG, "首条剥离: $userOriginal")
                    } else {
                        userOriginal = stripAll(raw, cfg)
                        Log.d(TAG, "不匹配剥离: $userOriginal")
                    }

                    if (userOriginal.isEmpty()) {
                        Log.d(TAG, "原文为空，跳过")
                        return
                    }

                    val target = TextProcessor.process(userOriginal, cfg)
                    if (target == raw) {
                        lastSet = target
                        return
                    }

                    Log.d(TAG, "写入: raw=$raw  userOriginal=$userOriginal  target=$target")

                    // 计算光标位置：原文经过替换（我→本喵、你→主人）后的长度，不包含喵和颜文字
                    val cursorPos = computeCursorPos(userOriginal, cfg)

                    // 尝试 SET_TEXT，失败则 fallback 到剪贴板粘贴
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

    /**
     * 计算光标位置：原文经过"我→本喵"、"你→主人"替换后的长度
     * 不包含句尾喵和颜文字，这样光标就停在"喵"前面
     */
    private fun computeCursorPos(original: String, cfg: CatConfig): Int {
        var pos = original.length
        if (cfg.enableWoToBenmiao) {
            // "我" 替换成 cfg.woReplacement（如"本喵"），长度 +1
            val woCount = original.count { it == '我' }
            pos += woCount * (cfg.woReplacement.length - 1)
        }
        if (cfg.enableNiToZhuren) {
            // "你" 替换成 cfg.niReplacement（如"主人"），长度 +1
            val niCount = original.count { it == '你' }
            pos += niCount * (cfg.niReplacement.length - 1)
        }
        return pos
    }

    /**
     * SET_TEXT 优先，失败时 fallback 到剪贴板粘贴
     */
    private fun setTextOrFallback(node: AccessibilityNodeInfo, text: String, cursorPos: Int): Boolean {
        // 优先 SET_TEXT
        if (setTextBeforeMeow(node, text, cursorPos)) return true

        Log.w(TAG, "SET_TEXT 失败，尝试剪贴板粘贴 fallback")
        // 剪贴板粘贴 fallback
        return try {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", text)
            clipboard.setPrimaryClip(clip)

            // 先清空输入框
            val clearBundle = Bundle()
            clearBundle.putCharSequence("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE", "")
            node.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, clearBundle)

            // 粘贴
            node.performAction(AccessibilityNodeInfo.ACTION_PASTE)

            // 恢复原始剪贴板内容（设为空）
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

    private fun findNodeByHint(n: AccessibilityNodeInfo?, hint: String): AccessibilityNodeInfo? {
        if (n == null) return null
        val text = n.text?.toString() ?: ""
        if (text.contains(hint, ignoreCase = true)) {
            return AccessibilityNodeInfo.obtain(n)
        }
        val contentDesc = n.contentDescription?.toString() ?: ""
        if (contentDesc.contains(hint, ignoreCase = true)) {
            return AccessibilityNodeInfo.obtain(n)
        }
        for (i in 0 until n.childCount) {
            val c = n.getChild(i) ?: continue
            val r = findNodeByHint(c, hint)
            c.recycle()
            if (r != null) return r
        }
        return null
    }

    private fun findNodeByClassName(n: AccessibilityNodeInfo?, className: String): AccessibilityNodeInfo? {
        if (n == null) return null
        if (className == n.className?.toString()) {
            return AccessibilityNodeInfo.obtain(n)
        }
        for (i in 0 until n.childCount) {
            val c = n.getChild(i) ?: continue
            val r = findNodeByClassName(c, className)
            c.recycle()
            if (r != null) return r
        }
        return null
    }

    private fun findEditable(n: AccessibilityNodeInfo?): AccessibilityNodeInfo? {
        if (n == null) return null
        if (n.isEditable) return AccessibilityNodeInfo.obtain(n)
        for (i in 0 until n.childCount) {
            val c = n.getChild(i) ?: continue
            val r = findEditable(c)
            c.recycle()
            if (r != null) return r
        }
        return null
    }

    /** 查找可聚焦且可编辑的节点（兜底策略） */
    private fun findFocusableEditable(n: AccessibilityNodeInfo?): AccessibilityNodeInfo? {
        if (n == null) return null
        if (n.isEditable && n.isFocusable) return AccessibilityNodeInfo.obtain(n)
        for (i in 0 until n.childCount) {
            val c = n.getChild(i) ?: continue
            val r = findFocusableEditable(c)
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
                if (st > 0 && result[st - 1] == ' ') {
                    st -= 1
                }
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