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
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources
import com.alterneo.alterneo_app.R
import com.alterneo.alterneo_app.databinding.FragmentHomeBinding
import com.alterneo.alterneo_app.ui.ClickHandler
import com.alterneo.alterneo_app.ui.FragmentStructure
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import com.mapbox.maps.plugin.scalebar.scalebar

class HomeFragment : FragmentStructure<FragmentHomeBinding>(), ClickHandler {
    override fun getClassBinding(): Class<FragmentHomeBinding> {
        return FragmentHomeBinding::class.java
    }

    private lateinit var bottomSheet: BottomSheetBehavior<LinearLayout>

    private var mapView: MapView? = null

    override fun viewCreated() {
        setHasOptionsMenu(true)

        binding.includeBottomSheet.handler = this

        bottomSheet = BottomSheetBehavior.from(binding.includeBottomSheet.llBottomSheet)
        mapView = binding.mapView
        mapView?.getMapboxMap()?.loadStyleUri(
            Style.MAPBOX_STREETS
        ) {
            mapView!!.scalebar.enabled = false
        }

        mapView?.getMapboxMap()?.addOnMapClickListener(onMapClickListener = {
            bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
            false
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_reload, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_reload -> {
                return true
            }
        }

        return false
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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.llBottomSheetTop -> {
                bottomSheet.state =
                    if (bottomSheet.state == BottomSheetBehavior.STATE_EXPANDED) BottomSheetBehavior.STATE_COLLAPSED else BottomSheetBehavior.STATE_EXPANDED
            }
        }
    }
}