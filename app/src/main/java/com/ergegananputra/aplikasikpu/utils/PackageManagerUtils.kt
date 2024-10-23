package com.ergegananputra.aplikasikpu.utils

import android.content.Context
import android.content.pm.PackageManager

object PackageManagerUtils {
    fun getAppVersion(context: Context): String {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            "Unknown"
        }
    }
}