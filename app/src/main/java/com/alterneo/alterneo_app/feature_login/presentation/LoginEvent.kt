package com.alterneo.alterneo_app.feature_login.presentation

sealed class LoginEvent {
    data class OnEmailChanged(val email: String) : LoginEvent()
    data class OnPasswordChanged(val password: String) : LoginEvent()
    object OnSubmit : LoginEvent()
    object OnSignupLinkClicked : LoginEvent()
}
