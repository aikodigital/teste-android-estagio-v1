package com.tiagomaciel.olhovivo.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.tiagomaciel.olhovivo.screens.viewModel.VehiclePositionViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun VehiclePositionsMapScreen(viewModel: VehiclePositionViewModel = viewModel()) {
    val locationPermissionState = rememberPermissionState(permission = android.Manifest.permission.ACCESS_FINE_LOCATION)
    val searchTerms = "1"

    LaunchedEffect(Unit) {
        locationPermissionState.launchPermissionRequest()
        viewModel.authenticateAndFetchData(type = "position", searchTerms = searchTerms)
    }

    val vehiclePositions by viewModel.vehiclePosition.observeAsState()
    val vehicleLines by viewModel.vehicleLines.observeAsState()
    val vehicleLStops by viewModel.vehicleStops.observeAsState()
    val error by viewModel.error.observeAsState()

    // Estado para controlar a posição da câmera no mapa
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(
                -23.5505,
                -46.6333
            ), 15f
        )
    }

    BoxWithConstraints {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                GoogleMap(
                    modifier = Modifier.height(500.dp),
                    cameraPositionState = cameraPositionState
                ) {
                    vehiclePositions?.l?.forEach { vehiclePosition ->
                        vehiclePosition.vs.filter {
                            vehiclePosition.lt1 == "AEROPORTO"
                        }.forEach { vehicle ->
                            Marker(
                                state = rememberMarkerState(position = LatLng(vehicle.py, vehicle.px)),
                                title = vehiclePosition.c,
                                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)
                            )
                        }
                    }
                    vehicleLStops?.forEach { stop ->
                        if (stop != null) {
                            Marker(
                                state = rememberMarkerState(position = LatLng(stop.py, stop.px)),
                                title = stop.np,
                                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
                            )
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(horizontal = 16.dp)
            ) {
                vehicleLines?.let { lines ->
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(lines) { line ->
                            Text(text = "Linha ${line?.cl}: ${line?.tp}")
                        }
                    }
                }
                error?.let {
                    Text(text = "Error: $it")
                }
            }
        }
    }
}