package com.alterneo.alterneo_app.ui.home

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources
import com.alterneo.alterneo_app.R
import com.alterneo.alterneo_app.databinding.FragmentHomeBinding
import com.alterneo.alterneo_app.models.Company
import com.alterneo.alterneo_app.models.Proposal
import com.alterneo.alterneo_app.responses.CompaniesResponse
import com.alterneo.alterneo_app.responses.ProposalsResponse
import com.alterneo.alterneo_app.ui.FragmentStructure
import com.alterneo.alterneo_app.utils.Client
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.OnPointAnnotationClickListener
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import com.mapbox.maps.plugin.scalebar.scalebar
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Response

class HomeFragment : FragmentStructure<FragmentHomeBinding>() {
    override fun getClassBinding(): Class<FragmentHomeBinding> {
        return FragmentHomeBinding::class.java
    }

    private lateinit var bottomSheet: BottomSheetBehavior<LinearLayout>

    private var mapView: MapView? = null

    override fun viewCreated() {
        setHasOptionsMenu(true)
        bottomSheet = BottomSheetBehavior.from(binding.includeBottomSheet.llBottomSheet)
        mapView = binding.mapView
        mapView?.getMapboxMap()?.loadStyleUri(
            Style.MAPBOX_STREETS
        ) {
            mapView!!.scalebar.enabled = false
            loadEverything()
        }

        mapView?.getMapboxMap()?.addOnMapClickListener(onMapClickListener = {
            if (bottomSheet.state == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
            }
            false
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_reload, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_reload -> {
                loadEverything()
                return true
            }
        }

        return false
    }

    private fun loadEverything() {
        Client.getClient(mainActivity).api.getCompanies(0)
            .enqueue(object : retrofit2.Callback<CompaniesResponse> {
                override fun onResponse(
                    call: Call<CompaniesResponse>,
                    r: Response<CompaniesResponse>
                ) {
                    r.body()?.data?.forEach {
                        makeProposalsCall(it.toCompany())
                    }
                }

                override fun onFailure(call: Call<CompaniesResponse>, t: Throwable) {
                }
            })
    }

    private fun makeProposalsCall(c: Company) {
        Client.getClient(mainActivity).api.getProposals(c.id)
            .enqueue(object : retrofit2.Callback<ProposalsResponse> {
                override fun onResponse(
                    call: Call<ProposalsResponse>,
                    response: Response<ProposalsResponse>
                ) {
                    addAnnotationToMap(c, response.body()!!.toProposals())
                }

                override fun onFailure(call: Call<ProposalsResponse>, t: Throwable) {
                }
            })
    }

    private fun addAnnotationToMap(c: Company, p: List<Proposal>) {
        // Create an instance of the Annotation API and get the PointAnnotationManager.
        val pin: Int = if (p.isNotEmpty()) R.drawable.pin_green else R.drawable.pin_red
        bitmapFromDrawableRes(
            mainActivity,
            pin
        )?.let {
            val annotationApi = mapView?.annotations
            val pointAnnotationManager = annotationApi?.createPointAnnotationManager(mapView!!)
            pointAnnotationManager?.addClickListener(OnPointAnnotationClickListener {
                clickedOnPin(c)
                true
            })
            // Set options for the resulting symbol layer.
            val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
                // Define a geographic coordinate.
                .withPoint(Point.fromLngLat(c.longitude!!, c.latitude!!))
                // Specify the bitmap you assigned to the point annotation
                // The bitmap will be added to map style automatically.
                .withIconImage(it)
            // Add the resulting pointAnnotation to the map.
            pointAnnotationManager?.create(pointAnnotationOptions)
        }
    }

    private fun clickedOnPin(c: Company) {
        Picasso.get().load(c.image).into(binding.includeBottomSheet.ivCompany)
        val camera: CameraOptions = CameraOptions.Builder()
            .center(Point.fromLngLat(c.longitude!!, c.latitude!!))
            .zoom(12.0)
            .pitch(0.0)
            .build()
        mapView?.getMapboxMap()
            ?.flyTo(camera, MapAnimationOptions.mapAnimationOptions { duration(1000) })
        binding.includeBottomSheet.company = c
        bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED
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
}