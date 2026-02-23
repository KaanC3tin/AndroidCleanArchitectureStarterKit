package com.karegraf.androidarchitecturestarterkit.presentation.permission

import com.karegraf.androidarchitecturestarterkit.domain.model.PermissionStatus
import com.karegraf.androidarchitecturestarterkit.presentation.Screen

data class PermissionItem(
    val title: String = "",
    val description: String = "",
    val status: PermissionStatus? = null,
    val route: String = "",
)