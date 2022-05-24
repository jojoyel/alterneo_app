package com.alterneo.alterneo_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alterneo.alterneo_app.feature_map.presentation.MapScreen
import com.alterneo.alterneo_app.feature_map.presentation.ProposalsScreen
import com.alterneo.alterneo_app.ui.theme.Alterneo_appTheme
import com.alterneo.alterneo_app.utils.Routes
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Routes.MAP_ROUTE) {
                composable(Routes.PROPOSALS_ROUTE) {
                    ProposalsScreen()
                }
                composable(Routes.MAP_ROUTE) {
                    MapScreen()
                }
            }
        }
    }
}