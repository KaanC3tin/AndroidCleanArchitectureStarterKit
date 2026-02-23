package com.karegraf.androidarchitecturestarterkit.data.manager

import com.karegraf.androidarchitecturestarterkit.domain.model.PermissionStatus
import com.karegraf.androidarchitecturestarterkit.domain.use_case.permission.CheckLocationPermissionUseCase
import javax.inject.Inject

class LocationPermissionManager @Inject constructor(private val checkLocationPermissionUseCase: CheckLocationPermissionUseCase) {
    fun check(): PermissionStatus = checkLocationPermissionUseCase()
}