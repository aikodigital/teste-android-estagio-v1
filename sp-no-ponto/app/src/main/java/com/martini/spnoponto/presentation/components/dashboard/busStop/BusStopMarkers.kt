package com.martini.spnoponto.presentation.components.dashboard.busStop

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.Marker
import com.martini.spnoponto.domain.entities.busStop.BusStop
import com.martini.spnoponto.domain.usecases.GetBusStopState

@Composable
fun BusStopMarkers(
    getBusStopViewModel: GetBusStopViewModel = hiltViewModel(),
    getBusStopForecastViewModel: GetBusStopForecastViewModel = hiltViewModel()
) {

    fun onClick(marker: Marker): Boolean {
        val busStop = marker.tag as BusStop? ?: return false
        getBusStopForecastViewModel.setStop(busStop)
        return false
    }

    when(val state = getBusStopViewModel.state.value) {
        is GetBusStopState.Loaded -> BusStopMarkersLoaded(
            stops = state.stops,
            onClick = { onClick(it) }
        )
        else -> {}
    }
}