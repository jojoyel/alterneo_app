package com.alterneo.alterneo_app.feature_register.domain.model

import com.google.gson.annotations.SerializedName

data class SignupModel(
    val email: String,
    val password: String,
    @SerializedName("last_name") val lastName: String? = null,
    @SerializedName("first_name") val firstName: String? = null,
    @SerializedName("account_type") val accountType: Int
)
