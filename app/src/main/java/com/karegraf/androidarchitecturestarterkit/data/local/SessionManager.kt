package com.karegraf.androidarchitecturestarterkit.data.local

import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit


@Singleton
class SessionManager @Inject constructor(
    private val tokenManager: TokenManager,
    private val sharedPreferences: SharedPreferences
) {

    val _sessionExpired = MutableStateFlow(false)
    val sessionExpired = _sessionExpired.asStateFlow()


    fun onSessionExpired() {
        tokenManager.clearToken()
        sharedPreferences.edit()
            .remove("Token")
            .apply()
        _sessionExpired.value = true
    }
}