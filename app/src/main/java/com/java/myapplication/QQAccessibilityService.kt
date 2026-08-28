package com.java.myapplication

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.os.PowerManager
import android.util.Log
import android.view.accessibility.AccessibilityEvent

/**
 * 无障碍服务，仅负责事件路由。
 * 实际业务逻辑委托给 TextReplaceEngine。
 * 服务配置完全依赖 XML（accessibility_service_config.xml），不在代码中动态覆盖。
 */
class QQAccessibilityService : AccessibilityService() {

    companion object {
        private const val TAG = "ZayuSvc"
        private const val WAKE_LOCK_TIMEOUT_MS = 30_000L
    }

    private var wakeLock: PowerManager.WakeLock? = null
    lateinit var engine: TextReplaceEngine
        private set

    override fun onServiceConnected() {
        super.onServiceConnected()
        // 不动态覆盖 serviceInfo —— 完全依赖 XML 配置
        // 避免丢失 canRetrieveWindowContent 等关键属性

        // 初始化引擎
        engine = TextReplaceEngine(this)

        // 获取唤醒锁（超时自动释放，避免屏幕关闭后持续耗电）
        val pm = getSystemService(POWER_SERVICE) as PowerManager
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "ZayuSvc:WakeLock")
        wakeLock?.acquire(WAKE_LOCK_TIMEOUT_MS)
        Log.d(TAG, "唤醒锁已获取（超时 ${WAKE_LOCK_TIMEOUT_MS}ms）")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        releaseWakeLock()
        Log.d(TAG, "唤醒锁已释放")
        return super.onUnbind(intent)
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return
        engine.onEvent(event)
    }

    override fun onInterrupt() {
        engine.onInterrupt()
    }

    private fun releaseWakeLock() {
        wakeLock?.let {
            if (it.isHeld) it.release()
        }
        wakeLock = null
    }
}