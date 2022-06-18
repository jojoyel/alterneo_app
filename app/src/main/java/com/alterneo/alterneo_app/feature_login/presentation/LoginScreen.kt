package com.alterneo.alterneo_app.feature_login.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alterneo.alterneo_app.utils.UiEvent

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(message = event.message)
                }
                is UiEvent.Navigate -> {
                    navController.navigate(event.route)
                }
                else -> Unit
            }
        }
    }

    Scaffold(scaffoldState = scaffoldState) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "Connexion à votre compte")

            Column() {
                OutlinedTextField(
                    value = viewModel.state.email,
                    onValueChange = { viewModel.onEvent(LoginEvent.OnEmailChanged(it)) })
                OutlinedTextField(
                    value = viewModel.state.password,
                    onValueChange = { viewModel.onEvent(LoginEvent.OnPasswordChanged(it)) })
                Button(onClick = { viewModel.onEvent(LoginEvent.OnSubmit) }) {
                    Text(text = "Se connecter")
                }
            }

            Row {
                Text(text = "Pas encore de compte ?")
                Text(
                    text = "Inscrivez-vous",
                    Modifier.clickable { viewModel.onEvent(LoginEvent.OnSignupLinkClicked) })
            }
        }
    }
}