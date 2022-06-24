package com.alterneo.alterneo_app.feature_map.data.remote.dto

import com.alterneo.alterneo_app.feature_map.domain.model.CompanyRegistration
import com.google.gson.annotations.SerializedName

data class CompanyRegistrationDto(
    val id: Int,
    val name: String,
    val description: String,
    val image: String,
    val url: String,
    val mail: String,
    val tel: String,
    @SerializedName("postal_code") val postalCode: String,
    val address: String,
    val city: String,
    @SerializedName("employees_number") val employeesNumber: Int,
    @SerializedName("creation_date") val creationDate: String,
    @SerializedName("SIRET") val siret: String,
    @SerializedName("account_creation_date") val accountCreationDate: String,
    val verified: Boolean,
    @SerializedName("business_sector_id") val businessSectorId: Int,
    @SerializedName("juridical_status_id") val juridicalStatusId: Int
) {
    fun toCompanyRegistration() = CompanyRegistration(
        accountCreationDate = accountCreationDate,
        address = address,
        businessSectorId = businessSectorId,
        city = city,
        creationDate = creationDate,
        description = description,
        employeesNumber = employeesNumber,
        id = id,
        image = image ?: "",
        juridicalStatusId = juridicalStatusId,
        mail = mail ?: "",
        name = name,
        postalCode = postalCode,
        siret = siret,
        tel = tel ?: "",
        url = url ?: "",
        verified = verified
    )
}