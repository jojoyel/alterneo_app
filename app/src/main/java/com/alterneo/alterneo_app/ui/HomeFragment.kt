package com.alterneo.alterneo_app.ui

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.content.res.AppCompatResources
import com.alterneo.alterneo_app.R
import com.alterneo.alterneo_app.databinding.FragmentHomeBinding
import com.alterneo.alterneo_app.models.Company
import com.alterneo.alterneo_app.models.Proposal
import com.alterneo.alterneo_app.responses.CompaniesResponse
import com.alterneo.alterneo_app.responses.ProposalsResponse
import com.alterneo.alterneo_app.utils.Client
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.OnPointAnnotationClickListener
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Response

class HomeFragment : FragmentStructure<FragmentHomeBinding>() {
    override fun getClassBinding(): Class<FragmentHomeBinding> {
        return FragmentHomeBinding::class.java
    }

    private var mapView: MapView? = null

    override fun viewCreated() {
        mapView = binding.mapView
        mapView?.getMapboxMap()?.loadStyleUri(
            Style.MAPBOX_STREETS,
            object : Style.OnStyleLoaded {
                override fun onStyleLoaded(style: Style) {
                }
            }
        )

       mapView?.getMapboxMap()?.addOnMapClickListener(onMapClickListener = {
            if (binding.cvCompany.visibility == View.VISIBLE) {
                val animator = ObjectAnimator.ofFloat(binding.cvCompany, View.TRANSLATION_Y, binding.cvCompany.height.toFloat() + 50)
                animator.start()
            }

            false
        })

        val call = Client.getClient(mainActivity).api.getCompanies(0)
        call.enqueue(object : retrofit2.Callback<CompaniesResponse> {
            override fun onResponse(
                call: Call<CompaniesResponse>,
                r: Response<CompaniesResponse>
            ) {
                r.body()?.data?.forEach {
                    makeProposalsCall(it.toCompany())
                }
            }

            override fun onFailure(call: Call<CompaniesResponse>, t: Throwable) {
                Log.d("LOGMOICA", "onFailure: " + t.message)
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
        val camera: CameraOptions = CameraOptions.Builder()
            .center(Point.fromLngLat(c.longitude!!, c.latitude!!))
            .zoom(12.0)
            .build()
        mapView?.getMapboxMap()?.setCamera(camera)
        binding.company = c
        Picasso.get().load(c.image).into(binding.ivCompany)
        binding.ivCompany
        val animator = ObjectAnimator.ofFloat(binding.cvCompany, View.TRANSLATION_Y, 200f)
        animator.start()
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