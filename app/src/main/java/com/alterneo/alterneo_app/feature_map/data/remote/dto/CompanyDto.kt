package com.alterneo.alterneo_app.feature_map.data.remote.dto

data class CompanyDto(
    val active: Boolean,
    val company_registration_id: Int,
    val id: Int,
    val image: String,
    val latitude: String,
    val longitude: String
)