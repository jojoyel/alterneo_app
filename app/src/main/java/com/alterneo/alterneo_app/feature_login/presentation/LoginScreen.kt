package com.alterneo.alterneo_app.feature_login.presentation

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alterneo.alterneo_app.R
import com.alterneo.alterneo_app.ui.theme.Alterneo_appTheme
import com.alterneo.alterneo_app.ui.theme.trainOneFont
import com.alterneo.alterneo_app.utils.UiEvent

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    var passwordVisible by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }
                is UiEvent.Navigate -> {
                    navController.navigate(event.route) {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }
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
                    text = "Alterneo",
                    fontSize = 32.sp,
                    fontFamily = trainOneFont,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.secondary
                )
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = stringResource(id = R.string.title_login),
                    fontSize = 26.sp
                )
                Spacer(Modifier.height(36.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    OutlinedTextField(
                        value = viewModel.state.email,
                        singleLine = true,
                        label = {
                            Text(stringResource(R.string.field_email))
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Mail,
                                contentDescription = stringResource(
                                    id = R.string.field_email
                                )
                            )
                        },
                        trailingIcon = {
                            if (viewModel.state.email.isNotBlank()) {
                                IconButton(onClick = {
                                    viewModel.onEvent(LoginEvent.OnEmailChanged(""))
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = stringResource(R.string.contentdesc_clear)
                                    )
                                }
                            }
                        },
                        isError = viewModel.state.emailError != null,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(
                                FocusDirection.Down
                            )
                        }),
                        onValueChange = { viewModel.onEvent(LoginEvent.OnEmailChanged(it)) })
                    AnimatedVisibility(
                        visible = viewModel.state.emailError != null,
                        enter = slideInVertically(),
                        exit = slideOutVertically()
                    ) {
                        Text(
                            text = if (viewModel.state.emailError != null) viewModel.state.emailError!!.asString(
                                context
                            ) else "",
                            color = MaterialTheme.colors.error
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = viewModel.state.password,
                        label = { Text(stringResource(id = R.string.field_password)) },
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Password,
                                contentDescription = stringResource(id = R.string.field_password)
                            )
                        },
                        isError = viewModel.state.passwordError != null,
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                if (passwordVisible) {
                                    Icon(
                                        imageVector = Icons.Filled.Visibility,
                                        contentDescription = stringResource(id = R.string.contentdesc_hide_password)
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Filled.VisibilityOff,
                                        contentDescription = stringResource(id = R.string.contentdesc_show_password)
                                    )
                                }
                            }
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {
                            focusManager.clearFocus()
                            viewModel.onEvent(LoginEvent.OnSubmit)
                        }),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        onValueChange = { viewModel.onEvent(LoginEvent.OnPasswordChanged(it)) })
                    AnimatedVisibility(
                        visible = viewModel.state.passwordError != null,
                        enter = slideInVertically(),
                        exit = slideOutVertically()
                    ) {
                        Text(
                            text = if (viewModel.state.passwordError != null) viewModel.state.passwordError!!.asString() else "",
                            color = MaterialTheme.colors.error
                        )
                    }
                    Spacer(modifier = Modifier.height(22.dp))
                    Button(
                        onClick = {
                            keyboardController?.hide()
                            viewModel.onEvent(LoginEvent.OnSubmit)
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                        shape = RoundedCornerShape(100),
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth(.5f)
                            .animateContentSize()
                    ) {
                        if (viewModel.state.isLoading) {
                            CircularProgressIndicator(color = MaterialTheme.colors.onSecondary)
                        } else {
                            Text(
                                text = stringResource(id = R.string.action_login),
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
                Row {
                    Text(text = stringResource(id = R.string.signup_catchphrase))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(id = R.string.action_signup),
                        Modifier.clickable { viewModel.onEvent(LoginEvent.OnSignupLinkClicked) },
                        color = MaterialTheme.colors.secondary
                    )
                }
            }
        }
    }
}