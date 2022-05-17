package com.alterneo.alterneo_app.feature_map.data.remote.dto

data class CompanyRegistrationDto(
    val account_creation_date: String,
    val adress: String,
    val business_sector_id: Int,
    val city: String,
    val creation_date: String,
    val description: String,
    val employees_number: Int,
    val id: Int,
    val image: String,
    val juridical_status_id: Int,
    val mail: String,
    val name: String,
    val postal_code: String,
    val siret: String,
    val status: String,
    val tel: String,
    val url: String,
    val verified: Boolean
)