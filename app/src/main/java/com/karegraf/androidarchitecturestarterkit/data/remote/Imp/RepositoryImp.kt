package com.karegraf.androidarchitecturestarterkit.data.remote.Imp

import com.karegraf.androidarchitecturestarterkit.data.remote.API
import com.karegraf.androidarchitecturestarterkit.data.remote.dto.LoginResponseDto
import com.karegraf.androidarchitecturestarterkit.data.remote.dto.toLoginModel
import com.karegraf.androidarchitecturestarterkit.domain.model.LoginModel
import com.karegraf.androidarchitecturestarterkit.domain.repository.MyRepository
import javax.inject.Inject

class RepositoryImp @Inject constructor(private val api: API) : MyRepository {
    override suspend fun login(username: String, password: String): LoginModel {
        val request = mapOf("username" to username, "password" to password)
        val dto: LoginResponseDto = api.login(request)
        return dto.toLoginModel()
    }
}