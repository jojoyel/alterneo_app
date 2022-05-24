package com.alterneo.alterneo_app.feature_map.presentation

import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alterneo.alterneo_app.feature_map.domain.use_case.GetCompaniesLocationsUseCase
import com.alterneo.alterneo_app.utils.Resource
import com.alterneo.alterneo_app.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapScreenViewModel @Inject constructor(
    private val getCompaniesLocationsUseCase: GetCompaniesLocationsUseCase
) : ViewModel() {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(MapScreenState())

    init {
        loadCompanies()
    }

    private fun loadCompanies(page: Int = 0) {
        getCompaniesLocationsUseCase(page).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state = state.copy(companies = result.data?.data?.map { companyDto -> companyDto.toCompany() }!!.toMutableList() )
                    if (result.data.totalReturned == 50) loadCompanies(page + 1)
                }
                is Resource.Error -> {
                    sendUiEvent(UiEvent.ShowSnackbar("Problème lors de la récupération des entreprises"))
                }
                is Resource.Loading -> {
                    sendUiEvent(UiEvent.ShowSnackbar("Ca charge"))
                }
            }
        }.launchIn(viewModelScope)
    }

    @OptIn(ExperimentalMaterialApi::class)
    fun onEvent(event: MapEvent) {
        when (event) {
            is MapEvent.OnMapClicked -> {
                state.selectedCompany = null
                sendUiEvent(UiEvent.MoveSheet(BottomSheetValue.Collapsed))
            }
            is MapEvent.OnPinClicked -> {
                state = state.copy(
                    selectedCompany = event.company
                )
                sendUiEvent(UiEvent.MoveSheet(BottomSheetValue.Expanded))
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}