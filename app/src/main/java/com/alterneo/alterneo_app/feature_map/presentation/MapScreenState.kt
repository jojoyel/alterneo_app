package com.alterneo.alterneo_app.feature_map.presentation

import com.alterneo.alterneo_app.feature_map.domain.model.Company
import com.mapbox.geojson.Point

data class MapScreenState(
    var companies: MutableList<Company> = arrayListOf(),
    var selectedCompany: Company? = null,
    var selectedCompanyProposalsLoading: Boolean = false,
    var mapCameraPosition: Point = Point.fromLngLat(4.035, 49.263)
)