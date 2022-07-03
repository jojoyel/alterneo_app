package com.alterneo.alterneo_app.feature_register.domain.use_case

import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {
    operator fun invoke(password: String) =
        password.isNotBlank() && password.matches(Regex("^((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%]).{8,32})\$"))
}