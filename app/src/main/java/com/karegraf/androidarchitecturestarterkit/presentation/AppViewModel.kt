package com.karegraf.androidarchitecturestarterkit.presentation


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karegraf.androidarchitecturestarterkit.data.local.SessionManager
import com.karegraf.androidarchitecturestarterkit.data.local.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class AppViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val sessionManager: SessionManager
) : ViewModel() {


    var startDestination by mutableStateOf(Screen.permission.route)
        private set

    // hasToken artık StateFlow üzerinden sağlanıyor ve UI recomposition'ı tetikliyor

    var hasToken by mutableStateOf(false)
        private set


    private val _navigateToLogin = MutableSharedFlow<Boolean>()

    val navigateToLogin = _navigateToLogin.asSharedFlow()

    // Yeni: Token akışını ViewModel içinde state olarak sakla
    val tokenState: StateFlow<String?> = tokenManager.tokenFlow()
        .stateIn(
            viewModelScope, SharingStarted.Eagerly,
            tokenManager.getToken()
        )

    // Yeni: isLoggedIn StateFlow (UI için doğrudan boolean akışı)

    val isLoggedIn: StateFlow<Boolean> = tokenState
        .map { token -> !token.isNullOrEmpty() }
        .stateIn(
            viewModelScope, SharingStarted.Eagerly,
            !tokenManager.getToken().isNullOrEmpty()
        )

    init {
        observeTokenExpired()
        observeTokenChanges()
    }

    private fun observeTokenChanges() {
        viewModelScope.launch {
            tokenState.collect { token ->
                val has = !token.isNullOrEmpty()
                hasToken = has
                startDestination = if (has) Screen.ihbarList.route else Screen.login.route
            }
        }
    }

    private fun observeTokenExpired() {
        viewModelScope.launch {
            sessionManager.sessionExpired.collect { expired ->
                if (expired) {
                    hasToken = true
                    _navigateToLogin.emit(true)
                }
            }
        }
    }

    private fun logout() {
        tokenManager.clearToken()
        sessionManager.onSessionExpired()
    }
}
