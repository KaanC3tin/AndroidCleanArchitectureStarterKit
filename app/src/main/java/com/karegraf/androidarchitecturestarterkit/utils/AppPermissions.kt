package com.karegraf.androidarchitecturestarterkit.utils

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi

object AppPermissions {
    const val CAMERA = Manifest.permission.CAMERA
    const val LOCATION = Manifest.permission.ACCESS_FINE_LOCATION

    // Android 13+ için READ_MEDIA_IMAGES, öncesi için READ_EXTERNAL_STORAGE
    val GALLERY: String
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
}