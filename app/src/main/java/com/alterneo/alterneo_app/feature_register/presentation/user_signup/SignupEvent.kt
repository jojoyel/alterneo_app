package com.alterneo.alterneo_app.feature_register.presentation.user_signup

sealed class SignupEvent {
    data class OnFirstNameChanged(val value: String) : SignupEvent()
    data class OnLastNameChanged(val value: String) : SignupEvent()
    data class OnEmailChanged(val value: String) : SignupEvent()
    data class OnPasswordChanged(val value: String) : SignupEvent()
    data class OnPasswordConfirmationChanged(val value: String) : SignupEvent()
    object OnSubmit : SignupEvent()
    object OnSigninLinkClicked : SignupEvent()
    object OnCompanySigninLinkClicked : SignupEvent()
}
