package com.alterneo.alterneo_app.feature_map.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("birth_date") val birthDate: String,
    val city: String,
    val country: String,
    val email: String,
    @SerializedName("first_name") val firstName: String,
    val id: Int,
    @SerializedName("last_name") val lastName: String,
    val misc: String,
    val password: String,
    @SerializedName("postal_code") val postalCode: String,
    val resume: String,
    val skills: String,
    val tel: String
)