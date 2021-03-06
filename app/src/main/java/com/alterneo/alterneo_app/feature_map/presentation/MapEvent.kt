package com.alterneo.alterneo_app.feature_map.presentation

import com.alterneo.alterneo_app.feature_map.domain.model.Company

sealed class MapEvent {
    object OnMapClicked : MapEvent()
    data class OnMapNotMoving(val lat: Double, val long: Double, val zoom: Double) : MapEvent()
    data class OnPinClicked(val company: Company) : MapEvent()
    data class OnDrawerEvent(val drawerEvent: DrawerEvent) : MapEvent()
    data class OnTopBarEvent(val topBarEvent: TopBarEvent) : MapEvent()
}
