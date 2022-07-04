package com.alterneo.alterneo_app.feature_register.presentation.company_signup

sealed class SignupEvent {
    object OnSubmit : SignupEvent()
    data class OnNameChanged(val name: String) : SignupEvent()
    data class OnEmailChanged(val email: String) : SignupEvent()
    data class OnTelChanged(val tel: String) : SignupEvent()
    data class OnUrlChanged(val url: String) : SignupEvent()
    data class OnPasswordChanged(val password: String) : SignupEvent()
    data class OnAddressChanged(val address: String) : SignupEvent()
    data class OnCityChanged(val city: String) : SignupEvent()
    data class OnPostalCodeChanged(val postalCode: String) : SignupEvent()
}
