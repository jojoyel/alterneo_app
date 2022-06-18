package com.alterneo.alterneo_app.feature_login.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginDto(
    val message: String,
    @SerializedName("jwt_token") val jwtToken: String,
    @SerializedName("refresh_token") val refreshToken: String
)
