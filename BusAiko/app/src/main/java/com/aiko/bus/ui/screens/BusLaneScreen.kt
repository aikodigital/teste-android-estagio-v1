package com.aiko.bus.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.aiko.bus.models.Stop
import com.aiko.bus.ui.components.HeaderNavigation
import com.aiko.bus.ui.theme.MAP_UI_SETTINGS
import com.aiko.bus.ui.theme.SAO_PAULO
import com.aiko.bus.ui.viewmodels.StopViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.ComposeMapColorScheme
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun BusLaneScreen(stopViewModel: StopViewModel, busLane: Int, navController: NavController) {
    val stops by stopViewModel.stops.collectAsState()

    val cameraPosition = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(SAO_PAULO, 11F)
    }

    LaunchedEffect(busLane) {
        stopViewModel.getStopsByBusLane(busLane)
    }


    Box(modifier = Modifier.fillMaxSize()) {
        MapView(cameraPosition, stops)

        HeaderNavigation {
            navController.navigate("bus_lanes")
        }

    }
}

@Composable
private fun MapView(
    cameraPosition: CameraPositionState,
    stops: List<Stop>
) {
    GoogleMap(
        modifier = Modifier
            .fillMaxSize(),
        uiSettings = MAP_UI_SETTINGS,
        cameraPositionState = cameraPosition
    ) {
        stops.forEach { stop ->
            Marker(
                state = MarkerState(position = LatLng(stop.latitude, stop.longitude)),
                title = stop.name
            )
        }
    }
}