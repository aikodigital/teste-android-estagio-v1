package com.hilguener.spbusao.presentation.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.hilguener.spbusao.R
import com.hilguener.spbusao.domain.model.Parades
import com.hilguener.spbusao.domain.model.Vehicles
import com.hilguener.spbusao.presentation.util.MyBitmapCache

@Composable
fun GoogleMapView(
    currentLocation: LatLng,
    cameraPositionState: CameraPositionState,
    uiSettings: MapUiSettings,
    properties: MapProperties,
    listPosVehicles: List<Vehicles>,
    listParades: List<Parades>,
    onMarkerClick: (String) -> Unit
) {
    val context = LocalContext.current
    val bitmapCache = remember { MyBitmapCache(context, 1) }


    val stopBitmap = remember { bitmapCache.getBitmap(R.drawable.location_pin, R.color.red) }
    val busBitmap = remember { bitmapCache.getBitmap(R.drawable.bus_stop, R.color.black) }


    val busIcon = remember { BitmapDescriptorFactory.fromBitmap(busBitmap!!) }
    val stopIcon = remember { BitmapDescriptorFactory.fromBitmap(stopBitmap!!) }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = properties,
        uiSettings = uiSettings,
    ) {
        listPosVehicles.forEach { posVehicle ->
            Marker(
                state = MarkerState(LatLng(posVehicle.latitude, posVehicle.longitude)),
                title = "Veículo ${posVehicle.prefixOfVehicle}",
                icon = busIcon,
            )
        }
        listParades.forEach { parade ->
            Marker(
                state = MarkerState(LatLng(parade.latitude, parade.longitude)),
                title = parade.nameOfParade,
                icon = stopIcon,
                onClick = {
                    onMarkerClick(parade.codeOfParade.toString())
                    true
                }
            )
        }
        LaunchedEffect(currentLocation) {
            if (currentLocation != LatLng(0.0, 0.0)) {
                cameraPositionState.animate(
                    CameraUpdateFactory.newCameraPosition(
                        CameraPosition.fromLatLngZoom(
                            currentLocation,
                            10f,
                        ),
                    ),
                    500,
                )
            }
        }
    }
}