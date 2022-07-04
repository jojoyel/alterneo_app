package com.alterneo.alterneo_app.feature_register.domain.use_case

import android.util.Patterns
import javax.inject.Inject

class ValidateUrlUseCase @Inject constructor() {
    operator fun invoke(url: String) =
        url.isNotBlank() && Patterns.WEB_URL.matcher(url).matches()
}
