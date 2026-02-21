package com.karegraf.androidarchitecturestarterkit.data.local

import kotlinx.coroutines.flow.Flow

interface TokenManager {
fun getToken() :String?
fun saveToken(token: String)
fun clearToken()
fun tokenFlow(): Flow<String?>
}