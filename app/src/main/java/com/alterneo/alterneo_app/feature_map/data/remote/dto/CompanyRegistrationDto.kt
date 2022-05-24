package com.alterneo.alterneo_app.feature_map.data.remote.dto

import com.alterneo.alterneo_app.feature_map.domain.model.CompanyRegistration
import com.google.gson.annotations.SerializedName

data class CompanyRegistrationDto(
    @SerializedName("account_creation_date") val accountCreationDate: String,
    val address: String,
    @SerializedName("business_sector_id") val businessSectorId: Int,
    val city: String,
    @SerializedName("creation_date") val creationDate: String,
    val description: String,
    @SerializedName("employees_number") val employeesNumber: Int,
    val id: Int,
    val image: String,
    @SerializedName("juridical_status_id") val juridicalStatusId: Int,
    val mail: String,
    val name: String,
    @SerializedName("postal_code") val postalCode: String,
    val siret: String,
    val status: String,
    val tel: String,
    val url: String,
    val verified: Boolean
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
        image = image,
        juridicalStatusId = juridicalStatusId,
        mail = mail,
        name = name,
        postalCode = postalCode,
        siret = siret,
        status = status,
        tel = tel,
        url = url,
        verified = verified

    )
}