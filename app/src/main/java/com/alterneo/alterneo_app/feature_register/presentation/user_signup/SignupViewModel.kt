package com.alterneo.alterneo_app.feature_register.presentation.user_signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alterneo.alterneo_app.feature_register.domain.use_case.UserSignupUseCase
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
    private val userSignupUseCase: UserSignupUseCase
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
            }
            is SignupEvent.OnLastNameChanged -> {
                state = state.copy(lastName = event.value)
            }
            is SignupEvent.OnEmailChanged -> {
                state = state.copy(email = event.value)
            }
            is SignupEvent.OnPasswordChanged -> {
                state = state.copy(password = event.value)
            }
            is SignupEvent.OnPasswordConfirmationChanged -> {
                state = state.copy(passwordConfirmation = event.value)
            }
            is SignupEvent.OnSubmit -> {
                if (state.isLoading) return
                doSignup()
            }
            is SignupEvent.OnSigninLinkClicked -> {
                sendUiEvent(UiEvent.Navigate(Route.LoginRoute.route))
            }
        }
    }

    fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}