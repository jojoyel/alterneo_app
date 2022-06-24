package com.alterneo.alterneo_app.feature_login.presentation

import com.alterneo.alterneo_app.core.UiText

data class LoginState(
    val email: String = "",
    val emailError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null,
    val isLoading: Boolean = false,
)