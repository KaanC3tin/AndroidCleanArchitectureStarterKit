package com.karegraf.androidarchitecturestarterkit.utils

import androidx.core.app.ComponentActivity

fun ComponentActivity.openAppSettings() {
    startActivity(
        android.content.Intent(
            android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            android.net.Uri.fromParts("package", packageName, null)
        )
    )
}