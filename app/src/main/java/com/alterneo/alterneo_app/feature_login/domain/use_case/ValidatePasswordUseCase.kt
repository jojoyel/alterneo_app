package com.alterneo.alterneo_app.feature_login.domain.use_case

import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {
    operator fun invoke(password: String) = password.isNotBlank()
}