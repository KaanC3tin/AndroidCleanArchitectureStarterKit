package com.karegraf.androidarchitecturestarterkit.domain.use_case.permission

import com.karegraf.androidarchitecturestarterkit.domain.model.PermissionStatus
import com.karegraf.androidarchitecturestarterkit.domain.repository.PermissionRepository
import com.karegraf.androidarchitecturestarterkit.utils.AppPermissions
import javax.inject.Inject


class CheckCameraPermissionUseCase @Inject constructor(
    private val repository: PermissionRepository) {
    operator fun invoke(): PermissionStatus{
        return repository.checkPermission(AppPermissions.CAMERA)
    }
}