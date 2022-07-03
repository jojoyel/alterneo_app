package com.alterneo.alterneo_app.feature_login.presentation

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alterneo.alterneo_app.R
import com.alterneo.alterneo_app.core.UiText
import com.alterneo.alterneo_app.feature_login.domain.use_case.DoLoginUseCase
import com.alterneo.alterneo_app.feature_login.domain.use_case.ValidateEmailUseCase
import com.alterneo.alterneo_app.feature_login.domain.use_case.ValidatePasswordUseCase
import com.alterneo.alterneo_app.utils.Constants
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
class LoginViewModel @Inject constructor(
    private val loginUseCase: DoLoginUseCase,
    private val sharedPreferences: SharedPreferences,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : ViewModel() {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(LoginState())

    init {
        sharedPreferences.getString(Constants.SHARED_PREF_JWT, null)?.let {
            sendUiEvent(UiEvent.Navigate(Route.MapRoute.route))
        }
        sharedPreferences.getString(Constants.SHARED_PREF_EMAIL, null)?.let {
            state = state.copy(email = it)
        }
    }

    private fun doLogin() {
        loginUseCase(state.email, state.password).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state = state.copy(isLoading = false)
                    sendUiEvent(UiEvent.Navigate(Route.MapRoute.route))
                }
                is Resource.Error -> {
                                state = state.copy(isLoading = false)
                    result.message?.toIntOrNull()?.let {
                        when (it) {
                            in 500..599 -> {
                                sendUiEvent(UiEvent.ShowSnackbar(UiText.StringResource(R.string.error_joining_server)))
                            }
                            400 -> {
                                state = state.copy(
                                    emailError = UiText.StringResource(R.string.invalid_creds),
                                    passwordError = UiText.StringResource(R.string.invalid_creds)
                                )
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

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailChanged -> {
                state = state.copy(email = event.email)
                if (state.emailError != null && validateEmailUseCase(state.email)) {
                    state = state.copy(emailError = null)
                    return
                }
            }
            is LoginEvent.OnPasswordChanged -> {
                state = state.copy(password = event.password)
                if (state.passwordError != null && validatePasswordUseCase(state.email)) {
                    state = state.copy(passwordError = null)
                    return
                }
            }
            is LoginEvent.OnSubmit -> {
                if (state.isLoading) return
                if (!validateEmailUseCase(state.email)) {
                    state = state.copy(emailError = UiText.StringResource(R.string.invalid_email))
                    return
                }
                if (!validatePasswordUseCase(state.password)) {
                    state =
                        state.copy(passwordError = UiText.StringResource(R.string.invalid_password))
                    return
                }
                doLogin()
            }
            is LoginEvent.OnSignupLinkClicked -> {
                sendUiEvent(UiEvent.Navigate(Route.UserSignupRoute.route))
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}