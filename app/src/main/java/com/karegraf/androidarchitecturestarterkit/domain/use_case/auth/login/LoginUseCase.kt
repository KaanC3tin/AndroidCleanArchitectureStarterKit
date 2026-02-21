package com.karegraf.androidarchitecturestarterkit.domain.use_case.auth.login

import com.karegraf.androidarchitecturestarterkit.data.remote.dto.LoginResponseDto
import com.karegraf.androidarchitecturestarterkit.domain.model.LoginModel
import com.karegraf.androidarchitecturestarterkit.domain.repository.MyRepository
import com.karegraf.androidarchitecturestarterkit.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: MyRepository) {


    fun executeLogin(Username: String, Password: String): Flow<Resource<LoginModel>> = flow {
        try {
            emit(Resource.Loading())
            val res = repository.login(
                Username = Username,
                Password = Password
            )
            emit(Resource.Success(data = res))
        } catch (e: Exception) {
            emit(Resource.Error(message = "$e (bir hata olustu)"))
        }

    }
}