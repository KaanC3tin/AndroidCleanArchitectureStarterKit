package com.karegraf.androidarchitecturestarterkit.presentation.common.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector


data class DialogStyle(
    val icon: ImageVector?= null,
    val iconTint: Color,
    val confirmButtonTextColor: Color,
    val confirmButtonContainerColor: Color?= null,
    val cancelButtonTextColor: Color,
    val cancelButtonContainerColor: Color,
)