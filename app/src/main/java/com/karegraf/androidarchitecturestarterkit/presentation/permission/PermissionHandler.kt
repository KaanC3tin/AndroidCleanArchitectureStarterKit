package com.karegraf.androidarchitecturestarterkit.presentation.permission

import android.Manifest
import android.icu.util.ICUUncheckedIOException
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.currentComposer
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.fragment.app.Fragment
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
