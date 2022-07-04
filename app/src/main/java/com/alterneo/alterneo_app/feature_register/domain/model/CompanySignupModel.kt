package com.alterneo.alterneo_app.feature_register.domain.model

import com.google.gson.annotations.SerializedName

data class CompanySignupModel(
    val email: String,
    val password: String,
    @SerializedName("account_type") val accountType: Int,
    val name: String,
    val url: String,
    val tel: String,
    @SerializedName("postal_code") val postalCode: String,
    val siret: String,
    val address: String,
    val city: String,
    val businessSectorId: Int,
    val juridicalStatusId: Int
)
