package com.alterneo.alterneo_app.feature_map.presentation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.alterneo.alterneo_app.R
import com.alterneo.alterneo_app.feature_map.domain.model.Company
import com.alterneo.alterneo_app.feature_map.presentation.composables.CompanyCard
import com.alterneo.alterneo_app.ui.theme.Alterneo_appTheme
import com.alterneo.alterneo_app.utils.UiEvent
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.OnPointAnnotationClickListener
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import com.mapbox.maps.plugin.scalebar.scalebar

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapScreen(
    viewModel: MapScreenViewModel = hiltViewModel()
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )

    var isSomethingLoading by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.MoveSheet -> {
                    when (event.bottomSheetValue) {
                        BottomSheetValue.Expanded -> {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        }
                        BottomSheetValue.Collapsed -> {
//                            bottomSheetScaffoldState.bottomSheetState.collapse()
                        }
                    }
                }
                is UiEvent.ShowSnackbar -> {
                    bottomSheetScaffoldState.snackbarHostState.showSnackbar(event.message)
                }
                is UiEvent.LoadingChange -> {
                    isSomethingLoading = event.loading
                }
                else -> {}
            }
        }
    }

    Alterneo_appTheme {
        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
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
                        CompanyCard(company = it)
                    }
                    Text(
                        text = "Bonjour",
                        Modifier
                            .padding(24.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            },
            modifier = Modifier.padding(0.dp),
            sheetPeekHeight = 40.dp
        ) { paddingValues ->
            Box(modifier = Modifier.fillMaxSize()) {
                if (isSomethingLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .zIndex(10f)
                            .size(32.dp)
                            .align(Alignment.TopEnd)
                            .offset(x = (-12).dp, y = 12.dp)
                    )
                }
                AndroidView(factory = {
                    View.inflate(it, R.layout.map_layout, null)
                },
                    update = {
                        val mapView = it.findViewById<MapView>(R.id.mapView)

                        mapView?.let { mv ->
                            mv.getMapboxMap().apply {
                                loadStyleUri(
                                    Style.MAPBOX_STREETS
                                ) {
                                    mv.scalebar.enabled = false
                                }
                                addOnMapClickListener(onMapClickListener = {
                                    viewModel.onEvent(MapEvent.OnMapClicked)
                                    false
                                })
                            }
                            viewModel.companies.forEach { company ->
                                addAnnotationToMap(mv, company) { c ->
                                    viewModel.onEvent(MapEvent.OnPinClicked(c))
                                }
                            }
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
                true
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