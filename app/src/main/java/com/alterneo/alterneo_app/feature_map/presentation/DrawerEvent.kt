package com.alterneo.alterneo_app.feature_map.presentation

sealed class DrawerEvent {
    object CloseClicked : DrawerEvent()
    object LogoutClicked : DrawerEvent()
}
