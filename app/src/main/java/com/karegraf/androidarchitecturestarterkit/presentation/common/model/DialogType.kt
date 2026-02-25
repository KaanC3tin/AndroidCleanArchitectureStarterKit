package com.karegraf.androidarchitecturestarterkit.presentation.common.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

enum class DialogType {
    ERROR,
    SUCCESS
}

@Composable
fun DialogType.toStyle(): DialogStyle {
    return when (this) {
        DialogType.SUCCESS -> DialogStyle(
            icon = Icons.Default.CheckCircle,
            iconTint = MaterialTheme.colorScheme.primary,
            confirmButtonTextColor = MaterialTheme.colorScheme.surface,
            confirmButtonContainerColor = MaterialTheme.colorScheme.primary,
            cancelButtonTextColor = MaterialTheme.colorScheme.primary,
            cancerButtonContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh

        )

        DialogType.ERROR -> DialogStyle(
            icon = Icons.Default.Error,
            iconTint = MaterialTheme.colorScheme.error,
            confirmButtonTextColor = MaterialTheme.colorScheme.onError,
            confirmButtonContainerColor = MaterialTheme.colorScheme.error,
            cancelButtonTextColor = MaterialTheme.colorScheme.error,
            cancerButtonContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        )
    }
}
