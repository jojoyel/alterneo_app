package com.alterneo.alterneo_app.feature_login.domain.use_case

import android.util.Patterns

class ValidateEmailUseCase {
    operator fun invoke(email: String) =
        email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
}