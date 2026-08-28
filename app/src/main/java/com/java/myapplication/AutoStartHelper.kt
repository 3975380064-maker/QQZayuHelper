package com.java.myapplication

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings

/**
 * 自启动/应用启动管理跳转工具。
 *
 * 各厂商没有统一 API，只能通过显式 ComponentName 逐个尝试跳转，失败就降级。
 * 这些类名是社区逆向出来的，厂商随时可能改，因此必须多候选 + try-catch。
 * 只能引导用户手动开启，第三方应用无权直接修改自启动权限（系统设计如此）。
 */
object AutoStartHelper {

    /** 跳转到自启动/应用启动管理页，返回是否成功跳转 */
    fun jump(context: Context): Boolean {
        val intents = getCandidates()
        for (intent in intents) {
            if (context.packageManager.resolveActivity(intent, 0) != null) {
                try {
                    context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                    return true
                } catch (_: Exception) {
                    // 继续试下一个
                }
            }
        }
        // 降级：应用详情页
        return try {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                .setData(Uri.fromParts("package", context.packageName, null))
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            true
        } catch (_: Exception) {
            false
        }
    }

    private fun getCandidates(): List<Intent> {
        val brand = Build.BRAND.lowercase()
        val components = when {
            brand.contains("huawei") || brand.contains("honor") -> listOf(
                "com.huawei.systemmanager/com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity",
                "com.huawei.systemmanager/com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity",
                "com.huawei.systemmanager/com.huawei.systemmanager.optimize.process.ProtectActivity"
            )
            brand.contains("xiaomi") || brand.contains("redmi") -> listOf(
                "com.miui.securitycenter/com.miui.permcenter.autostart.AutoStartManagementActivity",
                "com.miui.securitycenter/com.miui.permcenter.autostart.AutoStartManagementActivity"
            )
            brand.contains("oppo") || brand.contains("realme") -> listOf(
                "com.coloros.safecenter/com.coloros.safecenter.startupapp.StartupAppListActivity",
                "com.coloros.safecenter/com.coloros.safecenter.permission.startup.StartupAppListActivity",
                "com.oppo.safe/com.oppo.safe.permission.startup.StartupAppListActivity"
            )
            brand.contains("vivo") || brand.contains("iqoo") -> listOf(
                "com.vivo.permissionmanager/com.vivo.permissionmanager.activity.BgStartUpManagerActivity",
                "com.iqoo.secure/com.iqoo.secure.ui.phoneoptimize.BgStartUpManager",
                "com.iqoo.secure/com.iqoo.secure.safeguard.PurviewTabActivity"
            )
            brand.contains("oneplus") -> listOf(
                "com.oneplus.security/com.oneplus.security.chainlaunch.view.ChainLaunchAppListActivity"
            )
            brand.contains("meizu") -> listOf(
                "com.meizu.safe/com.meizu.safe.permission.PermissionMainActivity"
            )
            brand.contains("samsung") -> listOf(
                "com.samsung.android.lool/com.samsung.android.sm.ui.battery.BatteryActivity"
            )
            else -> emptyList()
        }
        return components.map {
            Intent().apply { component = ComponentName.unflattenFromString(it) }
        }
    }
}