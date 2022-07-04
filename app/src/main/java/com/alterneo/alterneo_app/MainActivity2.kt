package com.alterneo.alterneo_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alterneo.alterneo_app.feature_login.presentation.LoginScreen
import com.alterneo.alterneo_app.feature_map.presentation.MapScreen
import com.alterneo.alterneo_app.feature_register.presentation.company_signup.CompanySignupScreen
import com.alterneo.alterneo_app.feature_register.presentation.user_signup.SignupScreen
import com.alterneo.alterneo_app.utils.Route
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity2 :
    ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Route.LoginRoute.route) {
                composable(Route.LoginRoute.route) {
                    LoginScreen(navController)
                }
                composable(Route.MapRoute.route) {
                    MapScreen(navController)
                }
                composable(Route.UserSignupRoute.route) {
                    SignupScreen(navController)
                }
                composable(Route.CompanySignupRoute.route) {
                    CompanySignupScreen(navController)
                }
            }
        }
    }
}