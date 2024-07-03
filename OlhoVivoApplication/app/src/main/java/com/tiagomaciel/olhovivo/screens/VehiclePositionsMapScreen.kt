package com.tiagomaciel.olhovivo.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
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
    val searchTerms = "AEROPORTO"
    LaunchedEffect(Unit) {
        locationPermissionState.launchPermissionRequest()
        viewModel.authenticateAndFetchData(type = "position", searchTerms = searchTerms)
    }

    val vehiclePositions by viewModel.vehiclePosition.observeAsState()
    val vehicleLines by viewModel.vehicleLines.observeAsState()
    val error by viewModel.error.observeAsState()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(
                -23.5505,
                -46.6333
            ), 15f
        )
    }

    GoogleMap(
        cameraPositionState = cameraPositionState
    ) {
        vehiclePositions?.l?.forEach { vehiclePosition ->
            vehiclePosition.vs.filter {
                vehiclePosition.lt1 == searchTerms
            }?.forEach { vehicle ->
                Marker(
                    state = rememberMarkerState(position = LatLng(vehicle.py, vehicle.px)),
                    title = vehiclePosition.c
                )
            }
        }
//        vehicleLines?.let {
//            Marker(
//                state = rememberMarkerState(position = LatLng(it.py, vehicle.px)),
//                title = vehiclePosition.c
//            )
//        }
    }
    error?.let {
        Text(text = "Error: $it")
    }
}