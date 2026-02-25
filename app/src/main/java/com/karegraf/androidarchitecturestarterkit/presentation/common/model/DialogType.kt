package com.karegraf.androidarchitecturestarterkit.presentation.common.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.ui.graphics.vector.ImageVector

enum class DialogType(
    val label: String,
    val icon: ImageVector
) {
    ERROR(
        label = "Hata",
        icon = Icons.Default.Error
    ),
    SUCCESS(
        label = "Başarılı",
        icon = Icons.Default.CheckCircle
    )
}