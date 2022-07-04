package com.alterneo.alterneo_app.feature_register.presentation.company_signup

import com.alterneo.alterneo_app.core.UiText

data class CompanySignupState(
    val currentPage: Int = 1,
    val name: String = "",
    val nameError: UiText? = null,
    val email: String = "",
    val emailError: UiText? = null,
    val tel: String = "",
    val telError: UiText? = null,
    val url: String = "",
    val urlError: UiText? = null,
    val address: String = "",
    val addressError: UiText? = null,
    val city: String = "",
    val cityError: UiText? = null,
    val postalCode: String = "",
    val postalCodeError: UiText? = null,
    val creationDate: String = "",
    val creationDateError: UiText? = null,
    val siret: String = "",
    val siretError: UiText? = null,
    val businessSectorId: Int = -1,
    val businessSectorIdError: UiText? = null,
    val juridicalStatusId: Int = -1,
    val juridicalStatusIdError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null,
    val isLoading: Boolean = false
)
