package com.alterneo.alterneo_app.feature_login.presentation

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alterneo.alterneo_app.feature_login.domain.use_case.DoLoginUseCase
import com.alterneo.alterneo_app.utils.Constants
import com.alterneo.alterneo_app.utils.Resource
import com.alterneo.alterneo_app.utils.Routes
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
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(LoginState())

    init {
        sharedPreferences.getString(Constants.SHARED_PREF_JWT, null)?.let {
            sendUiEvent(UiEvent.Navigate(Routes.MAP_ROUTE))
        }
    }

    private fun doLogin() {
        loginUseCase(state.email, state.password).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    Log.d("LOGMOICA", "doLogin: ${result.data?.jwtToken}")
                    sendUiEvent(UiEvent.Navigate(Routes.MAP_ROUTE))
                }
                is Resource.Error -> {
                    sendUiEvent(UiEvent.ShowSnackbar("Identifiants incorrects"))
                }
                is Resource.Loading -> {
                    Log.d("LOGMOICA", "Ca charge")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailChanged -> {
                state = state.copy(email = event.email)
            }
            is LoginEvent.OnPasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is LoginEvent.OnSubmit -> {
                doLogin()
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}