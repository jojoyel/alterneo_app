package com.alterneo.alterneo_app.feature_register.presentation.user_signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alterneo.alterneo_app.R
import com.alterneo.alterneo_app.core.UiText
import com.alterneo.alterneo_app.feature_login.domain.use_case.ValidateEmailUseCase
import com.alterneo.alterneo_app.feature_register.domain.use_case.UserSignupUseCase
import com.alterneo.alterneo_app.feature_register.domain.use_case.ValidateNameUseCase
import com.alterneo.alterneo_app.feature_register.domain.use_case.ValidatePasswordUseCase
import com.alterneo.alterneo_app.utils.Resource
import com.alterneo.alterneo_app.utils.Route
import com.alterneo.alterneo_app.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val userSignupUseCase: UserSignupUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validateNameUseCase: ValidateNameUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : ViewModel() {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(SignUpState())

    private fun doSignup() {
        userSignupUseCase(
            state.email,
            state.password,
            state.lastName,
            state.firstName
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state = state.copy(isLoading = false)
                    sendUiEvent(UiEvent.Navigate(Route.LoginRoute.route))
                }
                is Resource.Error -> {
                    state = state.copy(isLoading = false)
                    result.message?.toIntOrNull()?.let {
                        when (it) {
                            in 500..599 -> {
                                sendUiEvent(UiEvent.ShowSnackbar(UiText.StringResource(R.string.error_joining_server)))
                            }
                            409 -> {
                                state =
                                    state.copy(emailError = UiText.StringResource(R.string.error_duplicate_email))
                            }
                            else -> {
                                sendUiEvent(UiEvent.ShowSnackbar(UiText.StringResource(R.string.error_occurred)))
                            }
                        }
                    } ?: run {
                        sendUiEvent(UiEvent.ShowSnackbar(UiText.DynamicString(result.message!!)))
                    }
                }
                is Resource.Loading -> {
                    state = state.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: SignupEvent) {
        when (event) {
            is SignupEvent.OnFirstNameChanged -> {
                state = state.copy(firstName = event.value)
                if (state.firstNameError != null && validateNameUseCase(state.firstName)) {
                    state = state.copy(firstNameError = null)
                    return
                }
            }
            is SignupEvent.OnLastNameChanged -> {
                state = state.copy(lastName = event.value)
                if (state.lastNameError != null && validateNameUseCase(state.lastName)) {
                    state = state.copy(lastNameError = null)
                    return
                }
            }
            is SignupEvent.OnEmailChanged -> {
                state = state.copy(email = event.value)
                if (state.emailError != null && validateEmailUseCase(state.email)) {
                    state = state.copy(emailError = null)
                    return
                }
            }
            is SignupEvent.OnPasswordChanged -> {
                state = state.copy(password = event.value)
                if (state.passwordError != null && validatePasswordUseCase(state.password)) {
                    state = state.copy(passwordError = null)
                    return
                }
            }
            is SignupEvent.OnPasswordConfirmationChanged -> {
                state = state.copy(passwordConfirmation = event.value)
                if (state.passwordConfirmationError != null && state.password == state.passwordConfirmation) {
                    state = state.copy(passwordConfirmationError = null)
                    return
                }
            }
            is SignupEvent.OnSubmit -> {
                if (state.isLoading) return
                if (!validateNameUseCase(state.lastName)) {
                    state =
                        state.copy(lastNameError = UiText.StringResource(R.string.invalid_last_name))
                    return
                }
                if (!validateNameUseCase(state.firstName)) {
                    state =
                        state.copy(firstNameError = UiText.StringResource(R.string.invalid_first_name))
                    return
                }
                if (!validateEmailUseCase(state.email)) {
                    state = state.copy(emailError = UiText.StringResource(R.string.invalid_email))
                    return
                }
                if (!validatePasswordUseCase(state.password)) {
                    state =
                        state.copy(passwordError = UiText.StringResource(R.string.invalid_password))
                    return
                }
                if (state.passwordConfirmation != state.password) {
                    state =
                        state.copy(passwordConfirmationError = UiText.StringResource(R.string.invalid_confirmation_password))
                    return
                }
                doSignup()
            }
            is SignupEvent.OnSigninLinkClicked -> {
                sendUiEvent(UiEvent.Navigate(Route.LoginRoute.route))
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}