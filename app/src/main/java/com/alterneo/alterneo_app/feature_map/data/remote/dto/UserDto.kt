package com.alterneo.alterneo_app.feature_map.data.remote.dto

data class UserDto(
    val birth_date: String,
    val city: String,
    val country: String,
    val email: String,
    val first_name: String,
    val id: Int,
    val last_name: String,
    val misc: String,
    val password: String,
    val postal_code: String,
    val resume: String,
    val skills: String,
    val tel: String
)