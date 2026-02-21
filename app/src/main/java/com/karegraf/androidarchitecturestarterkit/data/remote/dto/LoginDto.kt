package com.karegraf.androidarchitecturestarterkit.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.karegraf.androidarchitecturestarterkit.domain.model.LoginModel

data class LoginResponseDto(
    @SerializedName("Success") var Success: Boolean? = null,
    @SerializedName("Message") var Message: String? = null,
    @SerializedName("Token") var Token: String? = null
)

fun LoginResponseDto.toLoginModel(): LoginModel {
    return LoginModel(
        Success = this.Success ?: false,
        Message = this.Message ?: "",
        Token = this.Token ?: ""
    )
}