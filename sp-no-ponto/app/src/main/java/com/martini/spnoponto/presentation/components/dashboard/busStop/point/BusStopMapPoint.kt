package com.martini.spnoponto.presentation.components.dashboard.busStop.point

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.spnoponto.R
import com.martini.spnoponto.domain.usecases.GetBusStopForecastState
import com.martini.spnoponto.presentation.components.dashboard.busStop.GetBusStopForecastViewModel

@Composable
fun BusStopMapPoint(
    getBusStopForecastViewModel: GetBusStopForecastViewModel = hiltViewModel()
) {

    when (val state = getBusStopForecastViewModel.state.value) {
        is GetBusStopForecastState.Initial -> {
            BusStopMapPointLoading()
        }
        is GetBusStopForecastState.Loading -> {
            BusStopMapPointLoading()
        }
        is GetBusStopForecastState.TimeoutFailure -> {
            BusStopMapPointFailure(stringResource(R.string.connection_unavailable))
        }
        is GetBusStopForecastState.ServerFailure -> {
            BusStopMapPointFailure(stringResource(R.string.server_failure))
        }
        is GetBusStopForecastState.Failure -> {
            BusStopMapPointFailure(stringResource(R.string.unexpected_failure))
        }
        is GetBusStopForecastState.NoBusInfoFailure -> {
            BusStopMapPointFailure(stringResource(R.string.no_information_at_this_time))
        }
        is GetBusStopForecastState.Loaded -> {
            BusStopMapPointLoaded(busStopPoint = state.busStopPoint.busStopPoint)
        }
    }
}