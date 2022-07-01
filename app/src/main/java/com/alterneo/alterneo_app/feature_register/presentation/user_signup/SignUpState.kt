package com.alterneo.alterneo_app.feature_register.presentation.user_signup

import com.alterneo.alterneo_app.core.UiText

data class SignUpState(
    val firstName: String = "",
    val firstNameError: UiText? = null,
    val lastName: String = "",
    val lastNameError: UiText? = null,
    val email: String = "",
    val emailError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null,
    val passwordConfirmation: String = "",
    val passwordConfirmationError: UiText? = null,
    val isLoading: Boolean = false
)