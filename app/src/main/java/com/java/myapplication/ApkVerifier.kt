package com.java.myapplication

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import java.io.File
import java.security.MessageDigest

/**
 * APK 校验：判断一个下载来的安装包能不能装。
 *
 * 因为更新走的是第三方镜像，镜像返回的文件一律视为不可信，
 * 这里提供的是“是否可信、是否是更新”的判定原语，由 [UpdateChecker] 组织成完整流程。
 */
object ApkVerifier {

    private const val TAG = "ApkVerifier"

    /** 读取已安装应用自身的签名信息，作为可信基准。 */
    fun installedInfo(pm: PackageManager, packageName: String): PackageInfo? = try {
        @Suppress("DEPRECATION")
        pm.getPackageInfo(packageName, signerFlags())
    } catch (e: Exception) {
        Log.w(TAG, "读取已安装包信息失败", e)
        null
    }

    /** 解析 APK 文件。被镜像截断或损坏的包会在这里返回 null。 */
    fun readPackageInfo(pm: PackageManager, file: File): PackageInfo? = try {
        @Suppress("DEPRECATION")
        pm.getPackageArchiveInfo(file.absolutePath, signerFlags())
    } catch (e: Exception) {
        Log.w(TAG, "解析 APK 失败（可能是截断或损坏的包）", e)
        null
    }

    fun signerFlags(): Int =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            PackageManager.GET_SIGNING_CERTIFICATES
        } else {
            @Suppress("DEPRECATION")
            PackageManager.GET_SIGNATURES
        }

    /** 签名是否与已安装版本完全一致。取不到签名时一律判为不一致。 */
    fun hasSameSigner(installed: PackageInfo, candidate: PackageInfo): Boolean {
        val expected = signerDigests(installed)
        val actual = signerDigests(candidate)
        return expected.isNotEmpty() && expected == actual
    }

    fun versionCodeOf(info: PackageInfo): Long =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            info.longVersionCode
        } else {
            @Suppress("DEPRECATION")
            info.versionCode.toLong()
        }

    fun isNewer(candidate: PackageInfo, installed: PackageInfo): Boolean =
        versionCodeOf(candidate) > versionCodeOf(installed)

    private fun signerDigests(info: PackageInfo): Set<String> {
        val signatures = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            info.signingInfo?.apkContentsSigners
        } else {
            @Suppress("DEPRECATION")
            info.signatures
        } ?: return emptySet()
        return signatures.mapNotNull { signature ->
            signature?.toByteArray()?.let { sha256(it) }
        }.toSet()
    }

    private fun sha256(bytes: ByteArray): String =
        MessageDigest.getInstance("SHA-256").digest(bytes)
            .joinToString("") { "%02x".format(it) }
}