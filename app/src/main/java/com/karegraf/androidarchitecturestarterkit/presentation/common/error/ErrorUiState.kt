package com.karegraf.androidarchitecturestarterkit.presentation.common.error

data class ErrorUiState(
    val title: String = "",
    var message: String = "",
    val onRetry: () -> Unit = {}
) {
}