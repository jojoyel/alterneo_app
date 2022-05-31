package com.alterneo.alterneo_app.feature_map.domain.model

data class CompanyRegistration(
    val accountCreationDate: String = "",
    val address: String = "",
    val businessSectorId: Int = 0,
    val city: String = "",
    val creationDate: String = "",
    val description: String = "",
    val employeesNumber: Int = 0,
    val id: Int = 0,
    val image: String = "",
    val juridicalStatusId: Int  = 0,
    val mail: String = "",
    val name: String = "",
    val postalCode: String = "",
    val siret: String = "",
    val tel: String = "",
    val url: String = "",
    val verified: Boolean = false
)
