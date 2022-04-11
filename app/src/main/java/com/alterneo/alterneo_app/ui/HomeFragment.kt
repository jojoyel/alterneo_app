package com.alterneo.alterneo_app.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.util.Log
import androidx.appcompat.content.res.AppCompatResources
import com.alterneo.alterneo_app.R
import com.alterneo.alterneo_app.databinding.FragmentHomeBinding
import com.alterneo.alterneo_app.models.Company
import com.alterneo.alterneo_app.responses.CompaniesResponse
import com.alterneo.alterneo_app.utils.Client
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.OnPointAnnotationClickListener
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
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

        val call = Client.getClient(mainActivity).api.getCompanies(0)
        call.enqueue(object : retrofit2.Callback<CompaniesResponse> {
            override fun onResponse(
                call: Call<CompaniesResponse>,
                r: Response<CompaniesResponse>
            ) {
                r.body()?.data?.forEach {
                    addAnnotationToMap(it.toCompany())
                }
            }

            override fun onFailure(call: Call<CompaniesResponse>, t: Throwable) {
                Log.d("LOGMOICA", "onFailure: " + t.message)
            }
        })
    }

    private fun addAnnotationToMap(c: Company) {
    // Create an instance of the Annotation API and get the PointAnnotationManager.
        bitmapFromDrawableRes(
            mainActivity,
            R.drawable.pin_green
        )?.let {
            val annotationApi = mapView?.annotations
            val pointAnnotationManager = annotationApi?.createPointAnnotationManager(mapView!!)
            pointAnnotationManager?.addClickListener(object : OnPointAnnotationClickListener {
                override fun onAnnotationClick(annotation: PointAnnotation): Boolean {
                    clickedOnPin(c)
                    return true
                }
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
        Log.d("LOGMOICA", "clickedOnPin: " + c.city)
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