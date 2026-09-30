package com.java.myapplication

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent

/**
 * 无障碍服务，仅负责事件路由。
 * 实际业务逻辑委托给 TextReplaceEngine。
 * 服务配置完全依赖 XML（accessibility_service_config.xml），不在代码中动态覆盖。
 *
 * 注意：这里不再持有 WAKE_LOCK。无障碍服务本身由系统保活，
 * 之前 acquire(30s) 一次且不续期的唤醒锁对“防止 CPU 休眠”没有任何作用，只是白耗电。
 */
class QQAccessibilityService : AccessibilityService() {

    companion object {
        private const val TAG = "ZayuSvc"
    }

    lateinit var engine: TextReplaceEngine
        private set

    override fun onServiceConnected() {
        super.onServiceConnected()
        engine = TextReplaceEngine(this)
        Log.d(TAG, "无障碍服务已连接")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        if (::engine.isInitialized) {
            engine.release()
        }
        Log.d(TAG, "无障碍服务已解绑")
        return super.onUnbind(intent)
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return
        if (::engine.isInitialized) {
            engine.onEvent(event)
        }
    }

    override fun onInterrupt() {
        if (::engine.isInitialized) {
            engine.onInterrupt()
        }
    }
}