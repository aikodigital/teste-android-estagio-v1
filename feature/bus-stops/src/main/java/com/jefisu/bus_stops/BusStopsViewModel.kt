package com.jefisu.bus_stops

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.jefisu.common.asUiText
import com.jefisu.domain.BusConnectRepository
import com.jefisu.domain.util.Result
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.net.CookieManager

class BusStopsViewModel(
    private val repository: BusConnectRepository,
    private val savedStateHandle: SavedStateHandle,
    private val cookieManager: CookieManager
) : ViewModel() {

    var state by mutableStateOf(BusStopsState())
        private set

    private val navArgs = savedStateHandle.toRoute<BusStopsScreenRoute>()

    init {
        state = state.copy(lineCode = navArgs.lineCode)

        val cookieExpired = cookieManager.cookieStore.cookies.any { it.hasExpired() }

        viewModelScope.launch {
            if (cookieExpired) {
                async { repository.authenticate() }.await()
            }
            getBusStops()
        }
    }

    fun onAction(action: BusStopsAction) {
        when (action) {
            is BusStopsAction.OpenMapView -> {
                state = state.copy(showMapView = !state.showMapView)
            }

            is BusStopsAction.SelectBusStop -> {
                val stopCode = state.stops[action.stopIndex].stopCode
                getPredictionStop(stopCode)
            }

            is BusStopsAction.ChangeDirectionStops -> {
                state = state.copy(
                    stops = state.stops.reversed(),
                    isStartDirection = !state.isStartDirection
                )
            }

            BusStopsAction.AddFavoriteBusLine -> Unit
        }
    }

    private fun getBusStops() {
        viewModelScope.launch {
            val result = repository.getBusStops(navArgs.lineId)
            state = when (result) {
                is Result.Error -> state.copy(
                    error = result.error.asUiText()
                )

                is Result.Success -> state.copy(
                    stops = result.data,
                    error = null
                )
            }
            onAction(BusStopsAction.SelectBusStop(0))
        }
    }

    private fun getPredictionStop(stopCode: Int) {
        viewModelScope.launch {
            val result = repository.getBusArrivalPredictions(stopCode)
            state = when (result) {
                is Result.Error -> state.copy(
                    errorLoadPredictionStop = result.error.asUiText()
                )

                is Result.Success -> state.copy(
                    predictionStop = result.data,
                    errorLoadPredictionStop = null
                )
            }
        }
    }
}