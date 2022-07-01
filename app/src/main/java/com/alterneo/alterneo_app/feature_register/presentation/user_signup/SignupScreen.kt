package com.alterneo.alterneo_app.feature_register.presentation.user_signup

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alterneo.alterneo_app.R
import com.alterneo.alterneo_app.ui.theme.Alterneo_appTheme

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SignupScreen(viewModel: SignupViewModel = hiltViewModel()) {
    val scaffoldState = rememberScaffoldState()

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    Alterneo_appTheme {
        Scaffold(scaffoldState = scaffoldState) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.title_signup),
                    fontSize = 32.sp,
                    color = MaterialTheme.colors.secondary
                )
                Spacer(modifier = Modifier.height(28.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    OutlinedTextField(
                        value = viewModel.state.lastName,
                        singleLine = true,
                        label = {
                            Text(stringResource(R.string.field_lastname))
                        },
                        trailingIcon = {
                            if (viewModel.state.lastName.isNotBlank()) {
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
                            text = if (viewModel.state.firstNameError != null) viewModel.state.lastNameError!!.asString(
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
                            if (viewModel.state.firstName.isNotBlank()) {
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
                            text = if (viewModel.state.firstNameError != null) viewModel.state.firstNameError!!.asString(
                                context
                            ) else "",
                            color = MaterialTheme.colors.error
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}