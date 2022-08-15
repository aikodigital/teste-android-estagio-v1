package com.martini.spnoponto.presentation.components.dashboard.busStop

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.martini.spnoponto.constants.Constants
import com.martini.spnoponto.domain.usecases.GetBusStopForecastState
import com.martini.spnoponto.presentation.components.dashboard.busStop.point.BusStopMapPoint

@Composable
fun BusStopMap(
    getBusStopForecastViewModel: GetBusStopForecastViewModel = hiltViewModel()
) {
    val location = when(val state = getBusStopForecastViewModel.state.value) {
        is GetBusStopForecastState.Loaded -> {
            val stop = state.busStopPoint
            LatLng(stop.busStopPoint.latitude, stop.busStopPoint.longitude)
        }
        else -> LatLng(Constants.saoPauloLatitude, Constants.saoPauloLongitude)
    }

    val cameraPosition = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 15.54f)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        BusStopMapPoint()
        GoogleMap(
            modifier = Modifier
                .fillMaxSize(),
            cameraPositionState = cameraPosition
        ) {
            BusStopMarkers()
        }
    }
}