package com.karegraf.androidarchitecturestarterkit.presentation.common.model
data class DialogUiState(
    val type: DialogType,
    val title: String = "",
    val message: String = "",
    val confirmLabel: String = "Tamam",
    val dismissLabel: String = "Kapat",
    val onConfirm: () -> Unit = {},
    val onDismiss: () -> Unit = {}
)