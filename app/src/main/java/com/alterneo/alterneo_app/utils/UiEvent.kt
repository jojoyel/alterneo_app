package com.alterneo.alterneo_app.utils

import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi

sealed class UiEvent {
    object PopBackStack : UiEvent()
    data class LoadingChange(val loading: Boolean) : UiEvent()
    data class Navigate(val route: String) : UiEvent()
    data class ShowSnackbar(val message: String, val action: String? = null) : UiEvent()
    data class MoveSheet @OptIn(ExperimentalMaterialApi::class) constructor(val bottomSheetValue: BottomSheetValue) :
        UiEvent()

    data class MoveDrawer(val open: Boolean) : UiEvent()
}