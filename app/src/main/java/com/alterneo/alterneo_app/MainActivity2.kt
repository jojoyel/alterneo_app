package com.alterneo.alterneo_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alterneo.alterneo_app.feature_login.presentation.LoginScreen
import com.alterneo.alterneo_app.feature_map.presentation.MapScreen
import com.alterneo.alterneo_app.feature_map.presentation.ProposalsScreen
import com.alterneo.alterneo_app.feature_register.presentation.user_signup.SignupScreen
import com.alterneo.alterneo_app.utils.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity2 constructor() :
    ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Routes.LOGIN_ROUTE) {
                composable(Routes.PROPOSALS_ROUTE) {
                    ProposalsScreen()
                }
                composable(Routes.LOGIN_ROUTE) {
                    LoginScreen(navController)
                }
                composable(Routes.MAP_ROUTE) {
                    MapScreen(navController)
                }
                composable(Routes.USER_SIGNUP_ROUTE) {
                    SignupScreen()
                }
            }
        }
    }
}