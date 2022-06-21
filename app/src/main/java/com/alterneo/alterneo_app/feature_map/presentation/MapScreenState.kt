package com.alterneo.alterneo_app.feature_map.presentation

import com.alterneo.alterneo_app.feature_map.domain.model.Company

data class MapScreenState(
    var companies: MutableList<Company> = arrayListOf(),
    var selectedCompany: Company? = null,
    var selectedCompanyProposalsLoading: Boolean = false
)