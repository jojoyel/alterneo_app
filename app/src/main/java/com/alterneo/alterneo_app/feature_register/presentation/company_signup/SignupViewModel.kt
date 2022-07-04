package com.alterneo.alterneo_app.feature_register.presentation.company_signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alterneo.alterneo_app.R
import com.alterneo.alterneo_app.core.UiText
import com.alterneo.alterneo_app.feature_login.domain.use_case.ValidateEmailUseCase
import com.alterneo.alterneo_app.feature_register.domain.use_case.CompanySignupUseCase
import com.alterneo.alterneo_app.feature_register.domain.use_case.ValidateNameUseCase
import com.alterneo.alterneo_app.feature_register.domain.use_case.ValidatePasswordUseCase
import com.alterneo.alterneo_app.feature_register.domain.use_case.ValidateUrlUseCase
import com.alterneo.alterneo_app.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val companySignupUseCase: CompanySignupUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateUrlUseCase: ValidateUrlUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : ViewModel() {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(CompanySignupState())

    fun doSignup() {

    }

    fun onEvent(event: SignupEvent) {
        when (event) {
            is SignupEvent.OnNameChanged -> {
                state = state.copy(name = event.name)
                if (state.nameError != null && validateNameUseCase(state.name)) {
                    state =
                        state.copy(nameError = null)
                    return
                }
            }
            is SignupEvent.OnEmailChanged -> {
                state = state.copy(email = event.email)
                if (state.emailError != null && validateEmailUseCase(state.email)) {
                    state = state.copy(emailError = null)
                    return
                }
            }
            is SignupEvent.OnTelChanged -> {
                state = state.copy(tel = event.tel)
                if (state.telError != null && state.tel.isNotBlank()) {
                    state = state.copy(telError = null)
                    return
                }
            }
            is SignupEvent.OnUrlChanged -> {
                state = state.copy(url = event.url)
                if (state.urlError != null && validateUrlUseCase(state.url)) {
                    state = state.copy(urlError = null)
                    return
                }
            }
            is SignupEvent.OnPasswordChanged -> {
                state = state.copy(password = event.password)
                if (state.passwordError != null && validatePasswordUseCase(state.url)) {
                    state = state.copy(passwordError = null)
                    return
                }
            }
            is SignupEvent.OnAddressChanged -> {
                state = state.copy(address = event.address)
                if (state.addressError != null && state.address.isNotBlank()) {
                    state = state.copy(addressError = null)
                    return
                }
            }
            is SignupEvent.OnCityChanged -> {
                state = state.copy(address = event.city)
                if (state.cityError != null && state.city.isNotBlank()) {
                    state = state.copy(cityError = null)
                    return
                }
            }
            is SignupEvent.OnPostalCodeChanged -> {
                state = state.copy(address = event.postalCode)
                if (state.postalCodeError != null && state.postalCode.isNotBlank()) {
                    state = state.copy(postalCodeError = null)
                    return
                }
            }
            is SignupEvent.OnSubmit -> when (state.currentPage) {
                1 -> {
                    if (!validateNameUseCase(state.name)) {
                        state =
                            state.copy(nameError = UiText.StringResource(R.string.invalid_last_name))
                        return
                    }
                    state = state.copy(currentPage = state.currentPage + 1)
                }
                2 -> {
                    if (!validateEmailUseCase(state.email)) {
                        state =
                            state.copy(emailError = UiText.StringResource(R.string.invalid_email))
                        return
                    }
                    if (state.tel.isBlank()) {
                        state = state.copy(telError = UiText.StringResource(R.string.invalid_tel))
                        return
                    }
                    if (!validateUrlUseCase(state.url)) {
                        state = state.copy(urlError = UiText.StringResource(R.string.invalid_url))
                        return
                    }
                    if (!validatePasswordUseCase(state.password)) {
                        state =
                            state.copy(passwordError = UiText.StringResource(R.string.invalid_password))
                        return
                    }
                    state = state.copy(currentPage = state.currentPage + 1)

                }
                3 -> {
                    if (state.address.isNotBlank()) {
                        state =
                            state.copy(addressError = UiText.StringResource(R.string.invalid_address))
                        return
                    }
                    if (state.city.isNotBlank()) {
                        state = state.copy(cityError = UiText.StringResource(R.string.invalid_city))
                        return
                    }
                    if (state.postalCode.isNotBlank()) {
                        state =
                            state.copy(postalCodeError = UiText.StringResource(R.string.invalid_postal_code))
                        return
                    }
                    state = state.copy(currentPage = state.currentPage + 1)
                }
                4 -> doSignup()
            }
        }
    }

    fun sendUiEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }
}