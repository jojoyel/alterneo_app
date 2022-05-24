package com.alterneo.alterneo_app.feature_map.presentation

import com.alterneo.alterneo_app.feature_map.domain.model.Company
import com.mapbox.maps.MapView

data class MapScreenState(
    var companies: MutableList<Company> = arrayListOf(),
    var selectedCompany: Company? = null
)