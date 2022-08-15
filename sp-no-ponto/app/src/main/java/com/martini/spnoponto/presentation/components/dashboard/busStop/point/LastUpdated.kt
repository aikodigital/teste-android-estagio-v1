package com.martini.spnoponto.presentation.components.dashboard.busStop.point

import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.spnoponto.R
import com.martini.spnoponto.domain.usecases.GetBusStopForecastState
import com.martini.spnoponto.presentation.components.dashboard.busStop.GetBusStopForecastViewModel

@Composable
fun BusStopMapLastUpdated(
    getBusStopForecastViewModel: GetBusStopForecastViewModel = hiltViewModel()
) {
    when (val state = getBusStopForecastViewModel.state.value) {
        is GetBusStopForecastState.Loaded -> {
            Text(
                stringResource(
                    R.string.updated_at,
                    state.busStopPoint.time
                )
            )
        }
        is GetBusStopForecastState.ServerFailure -> {
            Text(stringResource(R.string.server_failure))
        }
        is GetBusStopForecastState.TimeoutFailure -> {
            Text(stringResource(R.string.connection_unavailable))
        }
        is GetBusStopForecastState.Failure -> {
            Text(stringResource(R.string.unexpected_failure))
        }
        is GetBusStopForecastState.Loading -> {
            LinearProgressIndicator()
        }
        else -> {
            LinearProgressIndicator()
        }
    }
}