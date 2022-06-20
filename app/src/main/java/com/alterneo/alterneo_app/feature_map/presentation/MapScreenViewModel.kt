package com.alterneo.alterneo_app.feature_map.presentation

import android.content.SharedPreferences
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alterneo.alterneo_app.feature_map.domain.model.Company
import com.alterneo.alterneo_app.feature_map.domain.use_case.GetCompaniesLocationsUseCase
import com.alterneo.alterneo_app.feature_map.domain.use_case.GetCompanyProposalUseCase
import com.alterneo.alterneo_app.feature_map.domain.use_case.GetCompanyRegistrationUseCase
import com.alterneo.alterneo_app.utils.Constants
import com.alterneo.alterneo_app.utils.Resource
import com.alterneo.alterneo_app.utils.Routes
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
    private val getCompaniesLocationsUseCase: GetCompaniesLocationsUseCase,
    private val getCompanyRegistrationUseCase: GetCompanyRegistrationUseCase,
    private val getCompanyProposalsUseCase: GetCompanyProposalUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(MapScreenState())

    var companies by mutableStateOf(ArrayList<Company>())

    init {
        loadCompanies()
    }

    private fun loadCompanies(page: Int = 0) {
        getCompaniesLocationsUseCase(page).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    sendUiEvent(UiEvent.LoadingChange(false))
                    companies =
                        (result.data?.data?.map { companyDto -> companyDto.toCompany() } as ArrayList<Company>?)!!
                    if (result.data?.totalReturned == 50) loadCompanies(page + 1)
                }
                is Resource.Error -> {
                    if (result.message == "403") {
                        sharedPreferences.edit().putString(Constants.SHARED_PREF_JWT, null).apply()
                        sendUiEvent(UiEvent.Navigate(Routes.LOGIN_ROUTE))
                    } else {
                        sendUiEvent(UiEvent.ShowSnackbar("Problème lors de la récupération des entreprises"))
                        sendUiEvent(UiEvent.LoadingChange(false))
                    }
                }
                is Resource.Loading -> {
                    sendUiEvent(UiEvent.LoadingChange(true))
                }
            }
        }.launchIn(viewModelScope)
    }

    @OptIn(ExperimentalMaterialApi::class)
    fun onEvent(event: MapEvent) {
        when (event) {
            is MapEvent.OnPinClicked -> {
                sendUiEvent(UiEvent.MoveSheet(BottomSheetValue.Expanded))
                viewModelScope.launch {
                    getCompanyRegistrationUseCase.invoke(event.company.companyRegistrationId)
                        .collect { result ->
                            when (result) {
                                is Resource.Success -> {
                                    val c =
                                        event.company.copy(companyRegistration = result.data?.toCompanyRegistration())
                                    state = state.copy(selectedCompany = c)
                                }
                                is Resource.Error -> {
                                    sendUiEvent(UiEvent.ShowSnackbar("Error"))
                                }
                                is Resource.Loading -> {
                                }
                            }
                        }
                    getCompanyProposalsUseCase.invoke(event.company.id)
                        .collect { result ->
                            when (result) {
                                is Resource.Success -> {
                                    val c =
                                        state.selectedCompany?.copy(proposals = result.data?.data?.map { it -> it.toProposal() })
                                    state = state.copy(selectedCompany = c)
                                }
                                is Resource.Loading -> {

                                }
                                is Resource.Error -> {

                                }
                            }
                        }
                }
            }
            is MapEvent.OnMapClicked -> {
//                sendUiEvent(UiEvent.MoveSheet(BottomSheetValue.Collapsed))
//                state = state.copy(selectedCompany = null)
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}