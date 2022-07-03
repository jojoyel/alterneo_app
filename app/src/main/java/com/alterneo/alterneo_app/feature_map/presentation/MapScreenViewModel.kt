package com.alterneo.alterneo_app.feature_map.presentation

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alterneo.alterneo_app.R
import com.alterneo.alterneo_app.core.UiText
import com.alterneo.alterneo_app.feature_map.domain.model.Company
import com.alterneo.alterneo_app.feature_map.domain.use_case.GetCompaniesLocationsUseCase
import com.alterneo.alterneo_app.feature_map.domain.use_case.GetCompanyProposalUseCase
import com.alterneo.alterneo_app.feature_map.domain.use_case.GetCompanyRegistrationUseCase
import com.alterneo.alterneo_app.utils.*
import com.mapbox.geojson.Point.fromLngLat
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

    private val _mapUiEvent = Channel<MapUiEvent>()
    val mapUiEvent = _mapUiEvent.receiveAsFlow()

    var state by mutableStateOf(MapScreenState())

    var companies by mutableStateOf(ArrayList<Company>())

    init {
        loadCompanies()
        sharedPreferences.getString(Constants.SHARED_PREF_LAST_POSITION, null)?.let {
            try {
                val values = it.split("/")
                state = state.copy(
                    mapCameraPosition = fromLngLat(
                        values[0].toDouble(),
                        values[1].toDouble()
                    )
                )
            } catch (e: NumberFormatException) {
            } catch (e: IndexOutOfBoundsException) {
            }
        }
    }

    private fun loadCompanies(page: Int = 0) {
        getCompaniesLocationsUseCase(page).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    sendMapUiEvent(MapUiEvent.CompaniesAdded((result.data?.data?.map { companyDto -> companyDto.toCompany() } as ArrayList<Company>?)!!))
                    companies =
                        (result.data?.data?.map { companyDto -> companyDto.toCompany() } as ArrayList<Company>?)!!
                    if (result.data?.totalReturned == 50) loadCompanies(page + 1)
                    else state = state.copy(dataLoading = false)
                }
                is Resource.Error -> {
                    if (result.message == "403") {
                        sharedPreferences.edit().putString(Constants.SHARED_PREF_JWT, null).apply()
                        sendUiEvent(UiEvent.Navigate(Route.LoginRoute.route))
                    } else {
                        sendUiEvent(UiEvent.ShowSnackbar(UiText.StringResource(R.string.error_fetching_companies)))
                        state = state.copy(
                            dataLoading = false
                        )
                    }
                }
                is Resource.Loading -> {
                    state = state.copy(
                        dataLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: MapEvent) {
        when (event) {
            is MapEvent.OnPinClicked -> {
                viewModelScope.launch {
                    getCompanyRegistrationUseCase.invoke(event.company.companyRegistrationId)
                        .collect { result ->
                            when (result) {
                                is Resource.Success -> {
                                    val c =
                                        event.company.copy(companyRegistration = result.data?.toCompanyRegistration())
                                    state = state.copy(
                                        selectedCompany = c
                                    )
                                }
                                is Resource.Error -> {
                                    sendUiEvent(UiEvent.ShowSnackbar(UiText.StringResource(R.string.error)))
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
                                    try {
                                        state = state.copy(
                                            selectedCompany = c,
                                            selectedCompanyProposalsLoading = false,
                                            mapCameraPosition = fromLngLat(
                                                c?.longitude!!.toDouble(),
                                                c.latitude.toDouble()
                                            )
                                        )
                                    } finally {
                                        state = state.copy(
                                            selectedCompany = c,
                                            selectedCompanyProposalsLoading = false
                                        )
                                    }
                                    sendUiEvent(UiEvent.MoveSheet(true))
                                }
                                is Resource.Loading -> {
                                    state = state.copy(
                                        selectedCompanyProposalsLoading = true
                                    )
                                }
                                is Resource.Error -> {
                                    state = state.copy(selectedCompanyProposalsLoading = false)
                                }
                            }
                        }
                }
                try {
                    onEvent(
                        MapEvent.OnMapNotMoving(
                            event.company.latitude.toDouble(),
                            event.company.longitude.toDouble(),
                            5.0
                        )
                    )
                } catch (e: NumberFormatException) {

                }
            }
            is MapEvent.OnDrawerEvent -> {
                processDrawerEvent(event.drawerEvent)
            }
            is MapEvent.OnTopBarEvent -> {
                sendUiEvent(UiEvent.MoveDrawer(true))
            }
            is MapEvent.OnMapNotMoving -> {
                sharedPreferences.edit().putString(
                    Constants.SHARED_PREF_LAST_POSITION,
                    ToolClass.join(event.lat, event.long, event.zoom.toFloat())
                ).apply()
            }
            else -> {}
        }
    }

    private fun processDrawerEvent(drawerEvent: DrawerEvent) {
        when (drawerEvent) {
            is DrawerEvent.LogoutClicked -> {
                sharedPreferences.edit {
                    remove(Constants.SHARED_PREF_JWT)
                    remove(Constants.SHARED_PREF_REFRESH_JWT)
                }
                sendUiEvent(UiEvent.Navigate(Route.LoginRoute.route))
            }
            is DrawerEvent.CloseClicked -> {
                sendUiEvent(UiEvent.MoveDrawer(false))
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    private fun sendMapUiEvent(event: MapUiEvent) {
        viewModelScope.launch {
            _mapUiEvent.send(event)
        }
    }
}