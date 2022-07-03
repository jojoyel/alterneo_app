package com.alterneo.alterneo_app.feature_register.domain.use_case

import javax.inject.Inject

class ValidateNameUseCase @Inject constructor() {
    operator fun invoke(name: String) =
        name.isNotBlank() && name.matches(Regex("^[-A-Za-zÀ-ÿ]{3,30}\$"))
}
