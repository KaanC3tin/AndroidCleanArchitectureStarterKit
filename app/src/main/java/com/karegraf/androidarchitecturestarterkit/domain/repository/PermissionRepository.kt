package com.karegraf.androidarchitecturestarterkit.domain.repository

import com.karegraf.androidarchitecturestarterkit.domain.model.PermissionStatus

interface PermissionRepository {
    fun checkPermission(permission: String): PermissionStatus
}