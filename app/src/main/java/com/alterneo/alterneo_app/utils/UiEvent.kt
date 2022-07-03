package com.alterneo.alterneo_app.utils

import com.alterneo.alterneo_app.core.UiText

sealed class UiEvent {
    object PopBackStack : UiEvent()
    data class Navigate(val route: String) : UiEvent()
    data class ShowSnackbar(val message: UiText, val action: String? = null) : UiEvent()
    data class MoveSheet(val expanded: Boolean) : UiEvent()
    data class MoveDrawer(val open: Boolean) : UiEvent()
}