package com.alterneo.alterneo_app.feature_login.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alterneo.alterneo_app.ui.theme.Alterneo_appTheme
import com.alterneo.alterneo_app.utils.UiEvent

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    var passwordVisible by remember { mutableStateOf(false) }

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

    Alterneo_appTheme {
        Scaffold(scaffoldState = scaffoldState) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Connexion à votre compte",
                    fontSize = 26.sp,
                    color = MaterialTheme.colors.secondary
                )
                Spacer(Modifier.height(36.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    OutlinedTextField(
                        value = viewModel.state.email,
                        singleLine = true,
                        label = {
                            Text("Email")
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Mail, contentDescription = "Email")
                        },
                        trailingIcon = {
                            if (viewModel.state.email.isNotBlank()) {
                                IconButton(onClick = {
                                    viewModel.state = viewModel.state.copy(email = "")
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Effacer"
                                    )
                                }
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        onValueChange = { viewModel.onEvent(LoginEvent.OnEmailChanged(it)) })
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = viewModel.state.password,
                        label = { Text("Mot de passe") },
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Password,
                                contentDescription = "Mot de passe"
                            )
                        },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                if (passwordVisible) {
                                    Icon(
                                        imageVector = Icons.Filled.Visibility,
                                        contentDescription = "Cacher le mot de passe"
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Filled.VisibilityOff,
                                        contentDescription = "Afficher le mot de passe"
                                    )
                                }
                            }
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        onValueChange = { viewModel.onEvent(LoginEvent.OnPasswordChanged(it)) })
                    Spacer(modifier = Modifier.height(22.dp))
                    Button(
                        onClick = { viewModel.onEvent(LoginEvent.OnSubmit) },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                        shape = RoundedCornerShape(100)
                    ) {
                        Text(text = "Se connecter")
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
                Row {
                    Text(text = "Pas encore de compte ?")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Inscrivez-vous",
                        Modifier.clickable { viewModel.onEvent(LoginEvent.OnSignupLinkClicked) },
                        color = MaterialTheme.colors.secondary
                    )
                }
            }
        }
    }
}