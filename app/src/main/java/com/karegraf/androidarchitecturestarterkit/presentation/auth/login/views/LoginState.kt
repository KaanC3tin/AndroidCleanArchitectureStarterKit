package com.karegraf.androidarchitecturestarterkit.presentation.auth.login.views

import com.karegraf.androidarchitecturestarterkit.domain.model.LoginModel

data class LoginState(
    val data: LoginModel? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)