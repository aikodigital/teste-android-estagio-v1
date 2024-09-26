package com.aiko.aikospvision.gmaps

import android.Manifest
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.aiko.domain.model.StopModel
import com.aiko.domain.model.ForecastModel
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

fun convertParadasToMapCordinators(stopModels: List<StopModel>): List<MapCordinator> {
    return stopModels.mapNotNull { parada ->
        if (parada.latitude != null && parada.longitude != null) {
            MapCordinator(
                nomeParada = parada.nomeParada ?: "Nome não disponível",
                paradaId = parada.codigoParada,
                latitude = parada.latitude,
                longitude = parada.longitude
            )
        } else {
            null
        }
    }
}

fun convertPrevisaoToMapCoordinators(forecastModel: ForecastModel?): List<MapCordinator> {
    val pontoParada = forecastModel?.pontoParada
    if (pontoParada == null || pontoParada.latitude == null || pontoParada.longitude == null) { return emptyList() }
    return listOf(
        MapCordinator(
            nomeParada = pontoParada.nomeParada ?: "Nome não disponível",
            latitude = pontoParada.latitude,
            longitude = pontoParada.longitude
        )
    )
}

data class MapCordinator(
    val nomeParada: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val paradaId: Int? = null
)

@Composable
fun MapComponent(
    paradas: List<MapCordinator>,
    userLocation: LatLng?
) {
    val validParadas = paradas.filter { it.latitude != null && it.longitude != null }
    if (validParadas.isNotEmpty() || userLocation != null) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            val initialCoordinates = userLocation ?: LatLng(validParadas[0].latitude!!, validParadas[0].longitude!!)
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(initialCoordinates, 16f)
            }

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                userLocation?.let { location ->
                    val userMarkerState = rememberMarkerState(position = location)
                    Marker(
                        state = userMarkerState,
                        title = "Minha Localização",
                        snippet = "Estou aqui"
                    )
                }
                validParadas.forEach { parada ->
                    parada.latitude?.let { lat ->
                        parada.longitude?.let { lon ->
                            val coordinates = LatLng(lat, lon)
                            val markerState = rememberMarkerState(position = coordinates)
                            Marker(
                                state = markerState,
                                title = parada.nomeParada ?: "Nome não disponível",
                                snippet = "Parada de ônibus"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CurrentLocationAndStopsMap(
    context: Context,
    paradas: List<MapCordinator>,
    @DrawableRes userIconResource: Int
) {
    var currentLocation by remember { mutableStateOf<LatLng?>(null) }
    var userIcon by remember { mutableStateOf<BitmapDescriptor?>(null) }

    LaunchedEffect(userIconResource) {
        userIcon = BitmapDescriptorFactory.fromResource(userIconResource)
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            getCurrentLocation(context) { location ->
                currentLocation = LatLng(location.latitude, location.longitude)
            }
        }
    }

    LaunchedEffect(Unit) {
        locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    MapComponent(
        paradas = paradas,
        userLocation = currentLocation,
        userIcon = userIcon
    )
}

@Composable
fun MapComponent(
    paradas: List<MapCordinator>,
    userLocation: LatLng?,
    userIcon: BitmapDescriptor?
) {
    val validParadas = paradas.filter { it.latitude != null && it.longitude != null }

    Box(modifier = Modifier.fillMaxSize()) {

        val initialCoordinates = userLocation ?: validParadas.firstOrNull()?.let {
            LatLng(it.latitude!!, it.longitude!!)
        }

        val cameraPositionState = rememberCameraPositionState {
            initialCoordinates?.let {
                position = CameraPosition.fromLatLngZoom(it, 16f)
            }
        }

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {

            userLocation?.let { location ->
                Marker(
                    state = rememberMarkerState(position = location),
                    icon = userIcon,
                    title = "Minha Localização",
                    snippet = "Estou aqui"
                )
            }


            validParadas.forEach { parada ->
                val coordinates = LatLng(parada.latitude!!, parada.longitude!!)
                Marker(
                    state = rememberMarkerState(position = coordinates),
                    title = parada.nomeParada ?: "Nome não disponível",
                    snippet = "Parada de ônibus"
                )
            }
        }
    }
}


@Composable
fun MapComponent(
    paradas: List<MapCordinator>,
    navController: NavController,
    onClickIsOn: Boolean = false
) {
    var selectedParada by remember { mutableStateOf<MapCordinator?>(null) }

    val validParadas = paradas.filter { it.latitude != null && it.longitude != null }

    if (validParadas.isNotEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            val firstCoordinates = LatLng(validParadas[0].latitude!!, validParadas[0].longitude!!)
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(firstCoordinates, 16f)
            }

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                validParadas.forEach { parada ->
                    parada.latitude?.let { lat ->
                        parada.longitude?.let { lon ->
                            val coordinates = LatLng(lat, lon)
                            val markerState = rememberMarkerState(position = coordinates)
                            Marker(
                                state = markerState,
                                title = parada.nomeParada ?: "Nome não disponível",
                                snippet = "Parada de ônibus",
                                onClick = {
                                    if(onClickIsOn){
                                        if (selectedParada == parada) {
                                            navController.navigate(
                                                "home/stops_screen/${parada.paradaId}/${
                                                    Uri.encode(
                                                        parada.nomeParada
                                                    )
                                                }"
                                            )
                                            selectedParada = null
                                        } else {
                                            selectedParada = parada
                                        }
                                    }
                                    false
                                }
                            )
                        }
                    }
                }
            }
        }
    }else{
        Box( modifier = Modifier.fillMaxSize() ) {
            GoogleMap()
        }
    }
}