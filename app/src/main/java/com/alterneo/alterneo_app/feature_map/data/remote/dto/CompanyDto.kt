package com.alterneo.alterneo_app.feature_map.data.remote.dto

import com.alterneo.alterneo_app.feature_map.domain.model.Company
import com.google.gson.annotations.SerializedName

data class CompanyDto(
    val active: Boolean,
    @SerializedName("company_registration_id") val companyRegistrationId: Int,
    val id: Int,
    val image: String,
    val latitude: String,
    val longitude: String
) {
    fun toCompany() = Company(
        companyRegistrationId = companyRegistrationId,
        active = active,
        id = id,
        image = image,
        latitude = latitude,
        longitude = longitude
    )
}