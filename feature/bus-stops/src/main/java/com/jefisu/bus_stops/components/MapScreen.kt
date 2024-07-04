package com.jefisu.bus_stops.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.jefisu.bus_stops.BusStopsState
import com.jefisu.bus_stops.util.calculateZoomLevel
import com.jefisu.bus_stops.util.findCentralVehiclePosition
import com.jefisu.ui.theme.PrimaryDark
import com.jefisu.common.R as CommonRes

@Composable
fun MapScreen(
    state: BusStopsState,
    modifier: Modifier = Modifier
) {
    state.predictionStop?.predictionStop?.let { predictionStop ->
        val latLng = remember(predictionStop) {
            findCentralVehiclePosition(predictionStop.lines)!!
        }
        val zoomLevel = remember(predictionStop) {
            calculateZoomLevel(predictionStop.lines.sumOf { it.vehicles.size })
        }
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(latLng, zoomLevel)
        }
        val uiSettings by remember {
            mutableStateOf(MapUiSettings(zoomControlsEnabled = true))
        }
        val properties by remember {
            mutableStateOf(MapProperties(mapType = MapType.TERRAIN))
        }

        GoogleMap(
            modifier = modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = properties,
            uiSettings = uiSettings
        ) {
            predictionStop.lines.forEach { line ->
                line.vehicles.forEach { vehicle ->
                    CustomMarker(
                        latLng = LatLng(vehicle.latitude, vehicle.longitude),
                        lineCode = line.code,
                        iconId = CommonRes.drawable.ic_bus_marker
                    )
                }
            }
        }
    }
}

@Composable
private fun CustomMarker(
    latLng: LatLng,
    lineCode: String,
    @DrawableRes iconId: Int
) {
    MarkerComposable(
        state = MarkerState(position = latLng)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = lineCode,
                fontWeight = FontWeight.Medium,
                color = PrimaryDark
            )
            Spacer(modifier = Modifier.height(2.dp))
            Image(
                painter = painterResource(id = iconId),
                contentDescription = null,
                modifier = Modifier.size(44.dp),
            )
        }
    }
}