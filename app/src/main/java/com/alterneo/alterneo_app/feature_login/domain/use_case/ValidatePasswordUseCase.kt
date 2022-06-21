package com.alterneo.alterneo_app.feature_login.domain.use_case

class ValidatePasswordUseCase {
    operator fun invoke(password: String) = password.isNotBlank()
}