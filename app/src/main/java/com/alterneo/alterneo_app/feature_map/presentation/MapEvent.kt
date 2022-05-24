package com.alterneo.alterneo_app.feature_map.presentation

import com.alterneo.alterneo_app.feature_map.domain.model.Company

sealed class MapEvent {
    object OnMapClicked: MapEvent()
    data class OnPinClicked(val company: Company): MapEvent()
}
