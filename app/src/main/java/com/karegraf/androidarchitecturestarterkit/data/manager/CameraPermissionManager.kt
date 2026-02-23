package com.karegraf.androidarchitecturestarterkit.data.manager

import com.karegraf.androidarchitecturestarterkit.domain.model.PermissionStatus
import com.karegraf.androidarchitecturestarterkit.domain.use_case.permission.CheckCameraPermissionUseCase
import javax.inject.Inject

class CameraPermissionManager @Inject constructor(private val checkCameraPermissionUseCase: CheckCameraPermissionUseCase) {
    fun check(): PermissionStatus = checkCameraPermissionUseCase()
}


