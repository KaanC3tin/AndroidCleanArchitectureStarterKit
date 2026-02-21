package com.karegraf.androidarchitecturestarterkit.data.remote

import com.karegraf.androidarchitecturestarterkit.data.remote.dto.LoginResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface API {
    @POST("login")
    suspend fun login(@Body request: Map<String, String>): LoginResponseDto
}