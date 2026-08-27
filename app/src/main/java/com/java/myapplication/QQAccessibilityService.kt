package com.java.myapplication

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Intent
import android.os.PowerManager
import android.util.Log
import android.view.accessibility.AccessibilityEvent

/**
 * 无障碍服务，仅负责事件路由。
 * 实际业务逻辑委托给 TextReplaceEngine。
 * 借鉴 AutoTask 的分离思想：Service 薄层，Engine 厚层。
 */
class QQAccessibilityService : AccessibilityService() {

    companion object {
        private const val TAG = "ZayuSvc"
        private const val PKG_QQ = "com.tencent.mobileqq"
        private const val PKG_QQI = "com.tencent.mobileqqi"
    }

    private var wakeLock: PowerManager.WakeLock? = null
    lateinit var engine: TextReplaceEngine
        private set

    override fun onServiceConnected() {
        super.onServiceConnected()
        val info = AccessibilityServiceInfo()
        info.eventTypes = AccessibilityEvent.TYPE_VIEW_CLICKED or
                AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED or
                AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
        info.flags = AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS or
                AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS
        info.notificationTimeout = 100
        info.packageNames = arrayOf(PKG_QQ, PKG_QQI)
        serviceInfo = info

        // 初始化引擎
        engine = TextReplaceEngine(this)

        // 获取唤醒锁
        val pm = getSystemService(POWER_SERVICE) as PowerManager
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "ZayuSvc:WakeLock")
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
        engine.onEvent(event)
    }

    override fun onInterrupt() {
        engine.onInterrupt()
    }
}