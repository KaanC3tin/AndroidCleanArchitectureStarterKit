package com.karegraf.androidarchitecturestarterkit.presentation.common.empty

data class EmptyUiState (
    val title:String="",
    val subTitle: String="",
    val description: String="",
    val onRetry: () -> Unit = {}
)