package com.example.storyappa.data.auth

import com.google.gson.annotations.SerializedName
import com.example.storyappa.data.lib.BaseResponse

// Login Data Class
data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    @field:SerializedName("loginResult")
    val loginResult: LoginResult
) : BaseResponse()

data class LoginResult(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("userId")
    val userId: String? = null,

    @field:SerializedName("token")
    val token: String? = null
)

// Register Data Class
data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)

class RegisterResponse : BaseResponse()