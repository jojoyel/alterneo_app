package com.alterneo.alterneo_app.feature_map.presentation

import com.alterneo.alterneo_app.feature_map.domain.model.Company

sealed class MapUiEvent {
    data class CompaniesAdded(val companies: ArrayList<Company>) : MapUiEvent()
}
