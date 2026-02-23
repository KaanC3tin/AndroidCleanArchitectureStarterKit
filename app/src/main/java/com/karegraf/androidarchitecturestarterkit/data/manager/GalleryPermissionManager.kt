package com.karegraf.androidarchitecturestarterkit.data.manager

import androidx.compose.runtime.Composable
import com.karegraf.androidarchitecturestarterkit.domain.model.PermissionStatus
import com.karegraf.androidarchitecturestarterkit.domain.use_case.permission.CheckGalleryPermissionUseCase
import javax.inject.Inject


class GalleryPermissionManager @Inject constructor(private val checkGalleryPermissionUseCase: CheckGalleryPermissionUseCase) {
    fun check(): PermissionStatus = checkGalleryPermissionUseCase()
}