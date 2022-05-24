package com.alterneo.alterneo_app.feature_map.domain.model

data class Company(
    val companyRegistration: CompanyRegistration? = null,
    val companyRegistrationId: Int = 0,
    val active: Boolean = false,
    val id: Int = 0,
    val image: String = "",
    val latitude: String = "",
    val longitude: String = ""
)