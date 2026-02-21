package com.karegraf.androidarchitecturestarterkit.data.remote

import android.content.SharedPreferences
import androidx.core.content.edit
import com.karegraf.androidarchitecturestarterkit.data.local.TokenManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class TokenManagerImp @Inject constructor(private val sharedPreferences: SharedPreferences) :
    TokenManager {


    private val _tokenState = MutableStateFlow(sharedPreferences.getString("Token", null))
    private val tokenState = _tokenState.asStateFlow()


    override fun getToken(): String {
        return sharedPreferences.getString("Token", null) ?: ""
    }

    override fun saveToken(token: String) {
        return sharedPreferences.edit {
            putString("Token", token)
            _tokenState.value = token
        }
    }

    override fun clearToken() {
        return sharedPreferences.edit {
            remove("Token")
            _tokenState.value = null
        }
    }

    override fun tokenFlow(): Flow<String?> = tokenState

}