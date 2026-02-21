package com.karegraf.androidarchitecturestarterkit.presentation.auth.login.views

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.karegraf.androidarchitecturestarterkit.domain.use_case.auth.login.LoginUseCase
import com.karegraf.androidarchitecturestarterkit.presentation.AppViewModel
import com.karegraf.androidarchitecturestarterkit.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : AppViewModel(
    sessionManager = null!!, tokenManager = null!!
) {


    private val _state = mutableStateOf(LoginState())

    val state = _state

    fun login(username: String, password: String) {
        loginUseCase.executeLogin(username, password).onEach {
            when (it) {
                is Resource.Loading<*> -> {
                    _state.value = LoginState(isLoading = true)
                }

                is Resource.Success -> {
                    val res = it.data
                    _state.value = LoginState(data = res)

                    if (res?.Success == true) {
                        println("Login successful: ${res}")
                    }

                }

                is Resource.Error<*> -> {
                    _state.value = LoginState(error = it.message ?: "Bilinmeyen bir hata oluştu")
                }
            }
        }.launchIn(viewModelScope)
    }
}
