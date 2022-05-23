package com.alterneo.alterneo_app.feature_map.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CompanyRegistrationDto(
    @SerializedName("account_creation_date") val account_creation_date: String,
    val adress: String,
    @SerializedName("business_sector_id") val business_sector_id: Int,
    val city: String,
    @SerializedName("creation_date") val creation_date: String,
    val description: String,
    @SerializedName("employees_number") val employees_number: Int,
    val id: Int,
    val image: String,
    @SerializedName("juridical_status_id") val juridical_status_id: Int,
    val mail: String,
    val name: String,
    @SerializedName("postal_code") val postal_code: String,
    val siret: String,
    val status: String,
    val tel: String,
    val url: String,
    val verified: Boolean
)