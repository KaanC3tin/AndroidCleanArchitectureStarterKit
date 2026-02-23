package com.karegraf.androidarchitecturestarterkit.presentation.permission

import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import com.karegraf.androidarchitecturestarterkit.domain.model.PermissionStatus

class PermissionHandler(
    private val activity: ComponentActivity,
    private val onResult: (permission: String, PermissionStatus?) -> Unit,
) {
    private var currentPermission: String = ""

    fun handleResult(isGranted: Boolean) {
        val status = when {
            isGranted -> PermissionStatus.Granted
            shouldShowRequestPermissionRationale(
                activity, currentPermission
            ) -> PermissionStatus.Denied
            else -> PermissionStatus.PermanentlyDenied
        }
        onResult(currentPermission, status)
    }

    fun request(permission: String) {
        currentPermission = permission
    }
}