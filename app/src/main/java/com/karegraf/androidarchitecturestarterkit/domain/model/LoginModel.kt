package com.karegraf.androidarchitecturestarterkit.domain.model

import com.google.gson.annotations.SerializedName

data class LoginModel(
    @SerializedName("Success") var Success: Boolean? = null,
    @SerializedName("Message") var Message: String? = null,
    @SerializedName("Token") var Token: String? = null
)