package com.alterneo.alterneo_app.feature_register.presentation.user_signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alterneo.alterneo_app.feature_register.domain.use_case.UserSignupUseCase
import com.alterneo.alterneo_app.utils.Resource
import com.alterneo.alterneo_app.utils.Routes
import com.alterneo.alterneo_app.utils.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignupViewModel @Inject constructor(
    private val userSignupUseCase: UserSignupUseCase
) : ViewModel() {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(SignUpState())

    fun doSignup() {
        userSignupUseCase(
            state.email,
            state.password,
            state.lastName,
            state.firstName
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {

                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
            }
        }
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
                sendUiEvent(UiEvent.Navigate(Routes.LOGIN_ROUTE))
            }
        }
    }

    fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}