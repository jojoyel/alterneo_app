package com.alterneo.alterneo_app.feature_register.presentation.user_signup

import android.annotation.SuppressLint
import androidx.compose.animation.*
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
import com.alterneo.alterneo_app.utils.UiEvent

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SignupScreen(
    navController: NavController,
    viewModel: SignupViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    var passwordVisible by remember { mutableStateOf(false) }
    var passwordConfirmationVisible by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
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
                    text = stringResource(id = R.string.title_signup),
                    fontSize = 26.sp,
                    color = MaterialTheme.colors.secondary
                )
                Spacer(modifier = Modifier.height(28.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    // LAST NAME
                    OutlinedTextField(
                        value = viewModel.state.lastName,
                        singleLine = true,
                        label = {
                            Text(stringResource(R.string.field_lastname))
                        },
                        trailingIcon = {
                            AnimatedVisibility(
                                visible = viewModel.state.lastName.isNotBlank(),
                                enter = slideInHorizontally(initialOffsetX = { it / 2 }) + fadeIn(),
                                exit = slideOutHorizontally() + fadeOut()
                            ) {
                                IconButton(onClick = {
                                    viewModel.onEvent(
                                        SignupEvent.OnLastNameChanged(
                                            ""
                                        )
                                    )
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = stringResource(R.string.contentdesc_clear)
                                    )
                                }
                            }
                        },
                        isError = viewModel.state.lastNameError != null,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }),
                        onValueChange = { viewModel.onEvent(SignupEvent.OnLastNameChanged(it)) }
                    )
                    AnimatedVisibility(
                        visible = viewModel.state.lastNameError != null,
                        enter = slideInVertically(),
                        exit = slideOutVertically()
                    ) {
                        Text(
                            text = if (viewModel.state.lastNameError != null) viewModel.state.lastNameError!!.asString(
                                context
                            ) else "",
                            color = MaterialTheme.colors.error
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    // FIRST NAME
                    OutlinedTextField(
                        value = viewModel.state.firstName,
                        singleLine = true,
                        label = {
                            Text(stringResource(R.string.field_firstname))
                        },
                        trailingIcon = {
                            AnimatedVisibility(
                                visible = viewModel.state.firstName.isNotBlank(),
                                enter = slideInHorizontally(initialOffsetX = { it / 2 }) + fadeIn(),
                                exit = slideOutHorizontally() + fadeOut()
                            ) {
                                IconButton(onClick = {
                                    viewModel.onEvent(
                                        SignupEvent.OnFirstNameChanged(
                                            ""
                                        )
                                    )
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = stringResource(R.string.contentdesc_clear)
                                    )
                                }
                            }
                        },
                        isError = viewModel.state.firstNameError != null,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }),
                        onValueChange = { viewModel.onEvent(SignupEvent.OnFirstNameChanged(it)) }
                    )
                    AnimatedVisibility(
                        visible = viewModel.state.firstNameError != null,
                        enter = slideInVertically(),
                        exit = slideOutVertically()
                    ) {
                        Text(
                            text = if (viewModel.state.firstNameError != null)
                                viewModel.state.firstNameError!!.asString(context)
                            else "",
                            color = MaterialTheme.colors.error
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    // EMAIL
                    OutlinedTextField(
                        value = viewModel.state.email,
                        singleLine = true,
                        label = {
                            Text(stringResource(R.string.field_email))
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = stringResource(
                                    id = R.string.contentdesc_email
                                )
                            )
                        },
                        trailingIcon = {
                            AnimatedVisibility(
                                visible = viewModel.state.email.isNotBlank(),
                                enter = slideInHorizontally(initialOffsetX = { it / 2 }) + fadeIn(),
                                exit = slideOutHorizontally() + fadeOut()
                            ) {
                                IconButton(onClick = {
                                    viewModel.onEvent(
                                        SignupEvent.OnEmailChanged(
                                            ""
                                        )
                                    )
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
                            focusManager.moveFocus(FocusDirection.Down)
                        }),
                        onValueChange = { viewModel.onEvent(SignupEvent.OnEmailChanged(it)) }
                    )
                    AnimatedVisibility(
                        visible = viewModel.state.emailError != null,
                        enter = slideInVertically(),
                        exit = slideOutVertically()
                    ) {
                        Text(
                            text = if (viewModel.state.emailError != null)
                                viewModel.state.emailError!!.asString(context)
                            else "",
                            color = MaterialTheme.colors.error
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    // PASSWORD
                    OutlinedTextField(
                        value = viewModel.state.password,
                        singleLine = true,
                        label = {
                            Text(stringResource(R.string.field_password))
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Password,
                                contentDescription = stringResource(id = R.string.field_password)
                            )
                        },
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
                        isError = viewModel.state.passwordError != null,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        onValueChange = { viewModel.onEvent(SignupEvent.OnPasswordChanged(it)) }
                    )
                    AnimatedVisibility(
                        visible = viewModel.state.passwordError != null,
                        enter = slideInVertically(),
                        exit = slideOutVertically()
                    ) {
                        Text(
                            text = if (viewModel.state.passwordError != null)
                                viewModel.state.passwordError!!.asString(context)
                            else "",
                            color = MaterialTheme.colors.error
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // PASSWORD CONFIRMATION
                OutlinedTextField(
                    value = viewModel.state.passwordConfirmation,
                    singleLine = true,
                    label = {
                        Text(stringResource(R.string.field_password_confirmation))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Password,
                            contentDescription = stringResource(id = R.string.field_password_confirmation)
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            passwordConfirmationVisible = !passwordConfirmationVisible
                        }) {
                            if (passwordConfirmationVisible) {
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
                    isError = viewModel.state.passwordConfirmationError != null,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                        viewModel.onEvent(SignupEvent.OnSubmit)
                    }),
                    visualTransformation = if (passwordConfirmationVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    onValueChange = { viewModel.onEvent(SignupEvent.OnPasswordConfirmationChanged(it)) }
                )
                AnimatedVisibility(
                    visible = viewModel.state.passwordConfirmationError != null,
                    enter = slideInVertically(),
                    exit = slideOutVertically()
                ) {
                    Text(
                        text = if (viewModel.state.passwordConfirmationError != null)
                            viewModel.state.passwordConfirmationError!!.asString(context)
                        else "",
                        color = MaterialTheme.colors.error
                    )
                }
                Spacer(modifier = Modifier.height(22.dp))
                Button(
                    onClick = {
                        keyboardController?.hide()
                        viewModel.onEvent(SignupEvent.OnSubmit)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                    shape = RoundedCornerShape(100),
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(.5F)
                        .animateContentSize()
                ) {
                    if (viewModel.state.isLoading) {
                        CircularProgressIndicator(color = MaterialTheme.colors.onSecondary)
                    } else {
                        Text(
                            text = stringResource(R.string.action_signup),
                            color = MaterialTheme.colors.onSecondary
                        )
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
                Row {
                    Text(text = stringResource(id = R.string.signin_catchphrase))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(id = R.string.action_login),
                        Modifier.clickable { viewModel.onEvent(SignupEvent.OnSigninLinkClicked) },
                        color = MaterialTheme.colors.secondary
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Text(text = stringResource(id = R.string.company_signup_catchphrase))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(id = R.string.action_signup),
                        Modifier.clickable { viewModel.onEvent(SignupEvent.OnCompanySigninLinkClicked) },
                        color = MaterialTheme.colors.secondary
                    )
                }
            }
        }
    }
}