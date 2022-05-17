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
import com.alterneo.alterneo_app.feature_map.presentation.ProposalsScreen
import com.alterneo.alterneo_app.ui.theme.Alterneo_appTheme
import com.alterneo.alterneo_app.utils.Routes
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Routes.PROPOSALS_ROUTE) {
                composable(Routes.PROPOSALS_ROUTE) {
                    ProposalsScreen()
                }
                composable(Routes.MAP_ROUTE) {
                    var mapView: State<MapView>
                    Alterneo_appTheme {
                        AndroidView(
                            factory = {
                                MapView(it)
                            },
                            modifier = Modifier.fillMaxSize()
                        ) { mapView ->
                            mapView.getMapboxMap().apply {
                                this.setCamera(
                                    cameraOptions = CameraOptions.Builder().zoom(6.0).center(
                                        Point.fromLngLat(4.035162350762649, 49.26335094209739)
                                    ).build()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}