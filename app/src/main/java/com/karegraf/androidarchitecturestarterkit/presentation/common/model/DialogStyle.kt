package com.karegraf.androidarchitecturestarterkit.presentation.common.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector


data class DialogStyle(
    val icon: ImageVector?= null,
    val iconTint: Color,
    val confirmButtonTextColor: Color,
    val confirmButtonContainerColor: Color,
    val cancelButtonTextColor: Color,
    val cancerButtonContainerColor: Color,
)