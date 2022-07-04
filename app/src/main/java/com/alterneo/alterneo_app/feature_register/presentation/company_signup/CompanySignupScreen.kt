@file:OptIn(ExperimentalComposeUiApi::class)

package com.alterneo.alterneo_app.feature_register.presentation.company_signup

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alterneo.alterneo_app.R
import com.alterneo.alterneo_app.ui.theme.Alterneo_appTheme
import com.alterneo.alterneo_app.utils.UiEvent

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CompanySignupScreen(
    navController: NavController,
    viewModel: SignupViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    val progressAnimation by animateFloatAsState(
        targetValue = ((viewModel.state.currentPage * 100 / 3).toFloat() / 100),
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
    )

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    navController.navigate(event.route)
                }
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message.asString(context))
                }
                else -> {}
            }
        }
    }

    Alterneo_appTheme {
        Scaffold(scaffoldState = scaffoldState) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight(.3f)
                        .padding(24.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Text(
                        text = stringResource(id = R.string.title_company_signup),
                        fontSize = 26.sp,
                        color = MaterialTheme.colors.secondary,
                        textAlign = TextAlign.Center,
                    )
                }
                LinearProgressIndicator(
                    progress = progressAnimation,
                    color = MaterialTheme.colors.secondary,
                    backgroundColor = MaterialTheme.colors.background,
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize()
                )
                Spacer(modifier = Modifier.height(20.dp))
                when (viewModel.state.currentPage) {
                    1 -> Part1(viewModel, keyboardController!!, focusManager, context)
                    2 -> Part2(viewModel, keyboardController!!, focusManager, context)
                    3 -> Part3(viewModel, keyboardController!!, focusManager, context)
                }
            }
        }
    }
}

@Composable
fun Part1(
    viewModel: SignupViewModel,
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager,
    context: Context
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            value = viewModel.state.name,
            singleLine = true,
            label = {
                Text(stringResource(R.string.field_company_name))
            },
            trailingIcon = {
                AnimatedVisibility(
                    visible = viewModel.state.name.isNotBlank(),
                    enter = slideInHorizontally(initialOffsetX = { it / 2 }) + fadeIn(),
                    exit = slideOutHorizontally() + fadeOut()
                ) {
                    IconButton(onClick = {
                        viewModel.onEvent(
                            SignupEvent.OnNameChanged(
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
            isError = viewModel.state.nameError != null,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
                viewModel.onEvent(SignupEvent.OnSubmit)
            }),
            onValueChange = { viewModel.onEvent(SignupEvent.OnNameChanged(it)) }
        )
        AnimatedVisibility(
            visible = viewModel.state.nameError != null,
            enter = slideInVertically(),
            exit = slideOutVertically()
        ) {
            Text(
                text = if (viewModel.state.nameError != null) viewModel.state.nameError!!.asString(
                    context
                ) else "",
                color = MaterialTheme.colors.error
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
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
            Text(
                text = stringResource(R.string.action_continue),
                color = MaterialTheme.colors.onSecondary
            )
        }
    }
}

@Composable
fun Part2(
    viewModel: SignupViewModel,
    keyboardController: SoftwareKeyboardController,
    focusManager: FocusManager,
    context: Context
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // EMAIL
        OutlinedTextField(
            value = viewModel.state.email,
            singleLine = true,
            label = { Text(stringResource(R.string.field_email)) },
            trailingIcon = {
                AnimatedVisibility(
                    visible = viewModel.state.email.isNotBlank(),
                    enter = slideInHorizontally(initialOffsetX = { it / 2 }) + fadeIn(),
                    exit = slideOutHorizontally() + fadeOut()
                ) {
                    IconButton(onClick = {
                        viewModel.onEvent(SignupEvent.OnEmailChanged(""))
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
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Next)
            }),
            onValueChange = { viewModel.onEvent(SignupEvent.OnEmailChanged(it)) }
        )
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

        // TEL
        OutlinedTextField(
            value = viewModel.state.tel,
            singleLine = true,
            label = {
                Text(stringResource(R.string.field_tel))
            },
            trailingIcon = {
                AnimatedVisibility(
                    visible = viewModel.state.tel.isNotBlank(),
                    enter = slideInHorizontally(initialOffsetX = { it / 2 }) + fadeIn(),
                    exit = slideOutHorizontally() + fadeOut()
                ) {
                    IconButton(onClick = {
                        viewModel.onEvent(
                            SignupEvent.OnTelChanged(
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
            isError = viewModel.state.telError != null,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Phone
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Next)
            }),
            onValueChange = { viewModel.onEvent(SignupEvent.OnTelChanged(it)) }
        )
        AnimatedVisibility(
            visible = viewModel.state.telError != null,
            enter = slideInVertically(),
            exit = slideOutVertically()
        ) {
            Text(
                text = if (viewModel.state.telError != null) viewModel.state.telError!!.asString(
                    context
                ) else "",
                color = MaterialTheme.colors.error
            )
        }

        // URL
        OutlinedTextField(
            value = viewModel.state.url,
            singleLine = true,
            label = {
                Text(stringResource(R.string.field_url))
            },
            trailingIcon = {
                AnimatedVisibility(
                    visible = viewModel.state.url.isNotBlank(),
                    enter = slideInHorizontally(initialOffsetX = { it / 2 }) + fadeIn(),
                    exit = slideOutHorizontally() + fadeOut()
                ) {
                    IconButton(onClick = {
                        viewModel.onEvent(
                            SignupEvent.OnUrlChanged(
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
            isError = viewModel.state.urlError != null,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Uri
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Next)
            }),
            onValueChange = { viewModel.onEvent(SignupEvent.OnUrlChanged(it)) }
        )
        AnimatedVisibility(
            visible = viewModel.state.urlError != null,
            enter = slideInVertically(),
            exit = slideOutVertically()
        ) {
            Text(
                text = if (viewModel.state.urlError != null) viewModel.state.urlError!!.asString(
                    context
                ) else "",
                color = MaterialTheme.colors.error
            )
        }

        // PASSWORD
        OutlinedTextField(
            value = viewModel.state.password,
            singleLine = true,
            label = {
                Text(stringResource(R.string.field_password))
            },
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    if (isPasswordVisible) {
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
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController.hide()
                viewModel.doSignup()
            }),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            onValueChange = { viewModel.onEvent(SignupEvent.OnPasswordChanged(it)) }
        )
        AnimatedVisibility(
            visible = viewModel.state.passwordError != null,
            enter = slideInVertically(),
            exit = slideOutVertically()
        ) {
            Text(
                text = if (viewModel.state.passwordError != null) viewModel.state.passwordError!!.asString(
                    context
                ) else "",
                color = MaterialTheme.colors.error
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                keyboardController.hide()
                viewModel.onEvent(SignupEvent.OnSubmit)
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
            shape = RoundedCornerShape(100),
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(.5F)
                .animateContentSize()
        ) {
            Text(
                text = stringResource(R.string.action_continue),
                color = MaterialTheme.colors.onSecondary
            )
        }
    }
}

@Composable
fun Part3(
    viewModel: SignupViewModel,
    keyboardController: SoftwareKeyboardController,
    focusManager: FocusManager,
    context: Context
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ADDRESS
        OutlinedTextField(
            value = viewModel.state.address,
            singleLine = true,
            label = { Text(stringResource(R.string.field_address)) },
            trailingIcon = {
                AnimatedVisibility(
                    visible = viewModel.state.address.isNotBlank(),
                    enter = slideInHorizontally(initialOffsetX = { it / 2 }) + fadeIn(),
                    exit = slideOutHorizontally() + fadeOut()
                ) {
                    IconButton(onClick = {
                        viewModel.onEvent(SignupEvent.OnAddressChanged(""))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.contentdesc_clear)
                        )
                    }
                }
            },
            isError = viewModel.state.addressError != null,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Next)
            }),
            onValueChange = { viewModel.onEvent(SignupEvent.OnAddressChanged(it)) }
        )
        AnimatedVisibility(
            visible = viewModel.state.addressError != null,
            enter = slideInVertically(),
            exit = slideOutVertically()
        ) {
            Text(
                text = if (viewModel.state.addressError != null) viewModel.state.addressError!!.asString(
                    context
                ) else "",
                color = MaterialTheme.colors.error
            )
        }
        // CITY
        OutlinedTextField(
            value = viewModel.state.city,
            singleLine = true,
            label = { Text(stringResource(R.string.field_city)) },
            trailingIcon = {
                AnimatedVisibility(
                    visible = viewModel.state.city.isNotBlank(),
                    enter = slideInHorizontally(initialOffsetX = { it / 2 }) + fadeIn(),
                    exit = slideOutHorizontally() + fadeOut()
                ) {
                    IconButton(onClick = {
                        viewModel.onEvent(SignupEvent.OnCityChanged(""))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.contentdesc_clear)
                        )
                    }
                }
            },
            isError = viewModel.state.cityError != null,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Next)
            }),
            onValueChange = { viewModel.onEvent(SignupEvent.OnCityChanged(it)) }
        )
        AnimatedVisibility(
            visible = viewModel.state.cityError != null,
            enter = slideInVertically(),
            exit = slideOutVertically()
        ) {
            Text(
                text = if (viewModel.state.cityError != null) viewModel.state.cityError!!.asString(
                    context
                ) else "",
                color = MaterialTheme.colors.error
            )
        }
        // POSTAL CODE
        OutlinedTextField(
            value = viewModel.state.city,
            singleLine = true,
            label = { Text(stringResource(R.string.field_postal_code)) },
            trailingIcon = {
                AnimatedVisibility(
                    visible = viewModel.state.postalCode.isNotBlank(),
                    enter = slideInHorizontally(initialOffsetX = { it / 2 }) + fadeIn(),
                    exit = slideOutHorizontally() + fadeOut()
                ) {
                    IconButton(onClick = {
                        viewModel.onEvent(SignupEvent.OnPostalCodeChanged(""))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.contentdesc_clear)
                        )
                    }
                }
            },
            isError = viewModel.state.postalCodeError != null,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController.hide()
                viewModel.onEvent(SignupEvent.OnSubmit)
            }),
            onValueChange = { viewModel.onEvent(SignupEvent.OnPostalCodeChanged(it)) }
        )
        AnimatedVisibility(
            visible = viewModel.state.postalCodeError != null,
            enter = slideInVertically(),
            exit = slideOutVertically()
        ) {
            Text(
                text = if (viewModel.state.postalCodeError != null) viewModel.state.postalCodeError!!.asString(
                    context
                ) else "",
                color = MaterialTheme.colors.error
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                keyboardController.hide()
                viewModel.onEvent(SignupEvent.OnSubmit)
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
            shape = RoundedCornerShape(100),
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(.5F)
                .animateContentSize()
        ) {
            Text(
                text = stringResource(R.string.action_continue),
                color = MaterialTheme.colors.onSecondary
            )
        }
    }
}