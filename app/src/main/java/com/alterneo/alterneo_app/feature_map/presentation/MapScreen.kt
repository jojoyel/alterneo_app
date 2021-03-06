package com.alterneo.alterneo_app.feature_map.presentation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alterneo.alterneo_app.R
import com.alterneo.alterneo_app.feature_map.domain.model.Company
import com.alterneo.alterneo_app.feature_map.presentation.composables.CompanyCard
import com.alterneo.alterneo_app.feature_map.presentation.composables.DrawerContent
import com.alterneo.alterneo_app.ui.theme.Alterneo_appTheme
import com.alterneo.alterneo_app.utils.UiEvent
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.OnPointAnnotationClickListener
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.scalebar.scalebar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapScreen(
    navController: NavController,
    viewModel: MapScreenViewModel = hiltViewModel()
) {
    val bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState
    )

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var mapView: MapView? = null

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.MoveSheet -> {
                    if (event.expanded) {
                        bottomSheetState.expand()
                    } else {
                        bottomSheetState.collapse()
                    }
                }
                is UiEvent.ShowSnackbar -> {
                    bottomSheetScaffoldState.snackbarHostState.showSnackbar(
                        event.message.asString(
                            context = context
                        )
                    )
                }
                is UiEvent.Navigate -> {
                    navController.navigate(event.route)
                }
                is UiEvent.MoveDrawer -> {
                    coroutineScope.launch {
                        if (event.open) {
                            bottomSheetScaffoldState.drawerState.open()
                        } else {
                            bottomSheetScaffoldState.drawerState.close()
                        }
                    }
                }
                else -> {}
            }
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.mapUiEvent.collect { event ->
            when (event) {
                is MapUiEvent.CompaniesAdded -> {
                    event.companies.forEach {
                        addAnnotationToMap(mapView, it) { c ->
                            viewModel.onEvent(MapEvent.OnPinClicked(c))
                        }
                    }
                }
            }
        }
    }

    Alterneo_appTheme {
        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            drawerGesturesEnabled = false,
            drawerContent = {
                DrawerContent() { event ->
                    viewModel.onEvent(MapEvent.OnDrawerEvent(event))
                }
            },
            topBar = {
                TopAppBar(
                    title = { Text(text = "Map") },
                    navigationIcon = {
                        IconButton(onClick = {
                            viewModel.onEvent(MapEvent.OnTopBarEvent(TopBarEvent.SandwichMenuClicked))
                        }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            },
            sheetContent = {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        modifier = Modifier
                            .width(38.dp)
                            .height(8.dp),
                        color = MaterialTheme.colors.primary,
                        shape = RoundedCornerShape(100)
                    ) {}
                    Spacer(modifier = Modifier.height(16.dp))
                    Divider(modifier = Modifier.padding(bottom = 8.dp))
                    viewModel.state.selectedCompany?.let {
                        CompanyCard(company = it, viewModel = viewModel)
                    }
                }
            },
            modifier = Modifier.padding(0.dp),
            sheetPeekHeight = 40.dp
        ) { _ ->
            Box(modifier = Modifier.fillMaxSize()) {
                if (viewModel.state.dataLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .zIndex(10f)
                            .size(32.dp)
                            .align(Alignment.TopEnd)
                            .offset(x = (-12).dp, y = 12.dp)
                    )
                }
                AndroidView(factory = { context ->
                    val initialCameraOptions = CameraOptions.Builder()
                        .center(viewModel.state.mapCameraPosition)
                        .zoom(6.0)
                        .build()
                    val mapInitOptions =
                        MapInitOptions(context = context, cameraOptions = initialCameraOptions)
                    val mv = MapView(context, mapInitOptions)
                    mv.getMapboxMap().apply {
                        loadStyleUri(
                            Style.MAPBOX_STREETS
                        ) {
                            mv.scalebar.enabled = false
                        }
                    }
                    mapView = mv
                    mv
                }, update = { mapView ->
                    mapView.let { mv ->
                        mv.camera.flyTo(
                            cameraOptions {
                                center(viewModel.state.mapCameraPosition)
                            }
                        )
                    }
                })
            }
        }
    }
}

private fun addAnnotationToMap(mapView: MapView?, c: Company, onClicked: (Company) -> Unit) {
    mapView?.let {
        bitmapFromDrawableRes(
            mapView.context, R.drawable.ic_pin_green
        )?.let {
            val annotationApi = mapView.annotations
            val pointAnnotationManager = annotationApi.createPointAnnotationManager()
            pointAnnotationManager.addClickListener(OnPointAnnotationClickListener {
                onClicked(c)
                false
            })

            val pointAnnotationOptions = PointAnnotationOptions()
            try {
                pointAnnotationOptions.withPoint(
                    Point.fromLngLat(
                        c.longitude.toDouble(),
                        c.latitude.toDouble()
                    )
                ).withIconImage(
                    bitmapFromDrawableRes(mapView.context, R.drawable.ic_pin_green)!!
                )
            } catch (e: NumberFormatException) {
                return
            }

            pointAnnotationManager.create(pointAnnotationOptions)
        }
    }
}

private fun bitmapFromDrawableRes(context: Context, @DrawableRes resourceId: Int) =
    convertDrawableToBitmap(AppCompatResources.getDrawable(context, resourceId))

private fun convertDrawableToBitmap(sourceDrawable: Drawable?): Bitmap? {
    if (sourceDrawable == null) {
        return null
    }
    return if (sourceDrawable is BitmapDrawable) {
        sourceDrawable.bitmap
    } else {
        // copying drawable object to not manipulate on the same reference
        val constantState = sourceDrawable.constantState ?: return null
        val drawable = constantState.newDrawable().mutate()
        val bitmap: Bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth, drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        bitmap
    }
}