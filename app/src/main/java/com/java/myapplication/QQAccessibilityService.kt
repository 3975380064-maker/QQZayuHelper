package com.java.myapplication

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
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
    }

    private var userOriginal = ""
    private var lastSet = ""
    private var processing = false
    private var lastWriteTime = 0L
    private var lastTextLength = 0
    private var wakeLock: PowerManager.WakeLock? = null

    private val handler = Handler(Looper.getMainLooper())
    private val idleTask = Runnable { doProcess() }

    /** 每次需要时直接读取 SharedPreferences，确保用户修改立即生效 */
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
        info.notificationTimeout = 50
        info.packageNames = arrayOf(PKG_QQ, PKG_QQI)
        serviceInfo = info

        // 获取唤醒锁，保持 CPU 运行
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
                processing = false
                userOriginal = ""
                lastSet = ""
                lastWriteTime = 0L
                lastTextLength = 0
            }

            AccessibilityEvent.TYPE_VIEW_CLICKED -> {
                // 点击发送按钮：不做任何文本替换，让消息原样发出
                val src = event.source ?: return
                src.recycle()
            }

            AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED -> {
                val cfg = loadConfig()
                val mode = cfg.processingMode

                // 判断文本是变长了还是变短了（删除操作）
                val cs = event.text ?: return
                val currentText = cs.toString()
                if (currentText.length < lastTextLength) {
                    // 删除操作：只更新长度记录，不触发处理
                    lastTextLength = currentText.length
                    return
                }
                lastTextLength = currentText.length

                if (mode == CatConfig.REAL_TIME_MODE) {
                    // 用户自定义空闲延迟
                    handler.removeCallbacks(idleTask)
                    handler.postDelayed(idleTask, cfg.idleDelayMs.toLong())
                } else {
                    // 标点模式
                    val root = rootInActiveWindow ?: return
                    val inp = findNodeById(root, ID_INPUT) ?: findEditable(root)
                    root.recycle()
                    if (inp == null) return

                    val text = inp.text
                    inp.recycle()
                    if (text == null || text.isEmpty()) return

                    val raw = text.toString().trim()
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

    fun doProcess() {
        if (processing) return
        processing = true

        val cfg = loadConfig()
        // 总开关：关闭时不处理任何内容
        if (!cfg.enabled) {
            processing = false
            return
        }

        val root = rootInActiveWindow ?: run {
            processing = false; return
        }

        var inp = findNodeById(root, ID_INPUT)
        if (inp == null) {
            inp = findEditable(root)
        }
        if (inp == null) {
            root.recycle()
            processing = false
            return
        }

        val cs = inp.text
        if (cs == null || cs.isEmpty()) {
            inp.recycle()
            root.recycle()
            processing = false
            userOriginal = ""
            lastSet = ""
            return
        }

        // ===== @mention 检测：文本包含 @ 时跳过所有处理（不破坏 ImageSpan）=====
        val rawStr = cs.toString()
        if (rawStr.contains('@')) {
            Log.d(TAG, "检测到 @，跳过处理")
            inp.recycle()
            root.recycle()
            processing = false
            return
        }

        val raw = rawStr.trim()
        if (raw.isEmpty()) {
            inp.recycle()
            root.recycle()
            processing = false
            userOriginal = ""
            lastSet = ""
            return
        }

        val now = System.currentTimeMillis()

        // 写入回显跳过
        if (lastWriteTime > 0 && now - lastWriteTime < 600 && lastSet == raw) {
            Log.d(TAG, "写入回显跳过")
            lastWriteTime = 0
            inp.recycle()
            root.recycle()
            processing = false
            return
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
            inp.recycle()
            root.recycle()
            processing = false
            return
        }

        val target = TextProcessor.process(userOriginal, cfg)
        if (target == raw) {
            lastSet = target
            inp.recycle()
            root.recycle()
            processing = false
            return
        }

        Log.d(TAG, "写入: raw=$raw  userOriginal=$userOriginal  target=$target")

        // 替换文本，并把光标放在"喵"之前（即用户原始内容末尾）
        val ok = setTextBeforeMeow(inp, target, userOriginal.length)
        if (ok) {
            lastSet = target
            lastWriteTime = System.currentTimeMillis()
        }

        inp.recycle()
        root.recycle()
        processing = false
    }

    /**
     * 替换文本，并把光标设置在"喵"之前（用户原始内容末尾位置）
     */
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