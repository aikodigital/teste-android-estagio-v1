package com.martini.spnoponto.presentation.components.lineDetails.content.maps

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.martini.spnoponto.constants.Constants


@Composable
fun LineDetailsMap() {

    val saoPauloLocation = LatLng(Constants.saoPauloLatitude, Constants.saoPauloLongitude)

    val cameraPosition = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(saoPauloLocation, Constants.saoPauloZ)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPosition
    ) {
        LinePositionGoogleMapMarker()
    }
}