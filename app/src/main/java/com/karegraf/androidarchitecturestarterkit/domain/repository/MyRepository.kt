package com.karegraf.androidarchitecturestarterkit.domain.repository

import com.karegraf.androidarchitecturestarterkit.data.remote.API
import com.karegraf.androidarchitecturestarterkit.domain.model.LoginModel
import javax.inject.Inject

interface MyRepository {
    suspend fun login(  Username: String, Password: String): LoginModel

}