package com.karegraf.androidarchitecturestarterkit.presentation.common.model

data class DialogUiState(
    val type: DialogType,
    val message: String = "",
    val confirmLabel: String = "Tamam",
    val dismissLabel: String = "İptal",
    val onConfirm: () -> Unit = {},
    val onDismiss: () -> Unit = {}
)