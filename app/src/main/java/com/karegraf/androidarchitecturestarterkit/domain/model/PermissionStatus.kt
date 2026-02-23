package com.karegraf.androidarchitecturestarterkit.domain.model

sealed class PermissionStatus {
    object Granted : PermissionStatus()
    object Denied : PermissionStatus()
    object PermanentlyDenied : PermissionStatus()

}