package com.aiko.bus.ui.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.aiko.bus.models.Line
import com.aiko.bus.models.Stop
import com.aiko.bus.models.Vehicle
import com.aiko.bus.ui.components.HeaderNavigation
import com.aiko.bus.ui.theme.MAP_UI_SETTINGS
import com.aiko.bus.ui.theme.SAO_PAULO
import com.aiko.bus.ui.theme.UPDATE_IN
import com.aiko.bus.ui.viewmodels.StopViewModel
import com.aiko.bus.ui.viewmodels.VehiclesViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch


@Composable
fun LineScreen(
    vehiclesViewModel: VehiclesViewModel,
    stopViewModel: StopViewModel,
    navController: NavController,
    line: Line
) {
    val vehicles by vehiclesViewModel.vehicles.collectAsState()
    val isLoading by vehiclesViewModel.isLoading.collectAsState()

    val stops by stopViewModel.stops.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(line) {
        stopViewModel.getStopsByLine(line.id)

        coroutineScope.launch {
            while (isActive) {
                vehiclesViewModel.getVehicles(line.id)
                delay(UPDATE_IN)
            }
        }
    }


    Box(modifier = Modifier.fillMaxSize()) {
        MapView(vehicles, stops, Modifier.fillMaxSize())

        HeaderNavigation {
            navController.navigate("lines")
        }

        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Linha ${line.identifier}",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Button(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .clip(CircleShape),
                        enabled = !isLoading,
                        contentPadding = PaddingValues(16.dp),
                        onClick = { vehiclesViewModel.getVehicles(line.id) }
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(Icons.Filled.Refresh, contentDescription = "Atualizar")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Origem:",
                            style = MaterialTheme.typography.labelMedium,
                            fontSize = 18.sp
                        )
                        Text(
                            text = line.origin,
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 18.sp
                        )
                    }
                    Column {
                        Text(
                            text = "Destino:",
                            style = MaterialTheme.typography.labelMedium,
                            fontSize = 18.sp
                        )
                        Text(
                            text = line.destination,
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 18.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = "Número de ônibus",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "${vehicles.size} ônibus em operação",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}


@Composable
fun MapView(vehicles: List<Vehicle>, stops: List<Stop>, modifier: Modifier = Modifier) {
    val cameraPosition = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(SAO_PAULO, 11F)
    }

    GoogleMap(
        modifier = modifier,
        uiSettings = MAP_UI_SETTINGS,
        cameraPositionState = cameraPosition
    ) {
        vehicles.forEach { vehicle ->
            Marker(
                state = MarkerState(position = LatLng(vehicle.latitude, vehicle.longitude)),
                title = vehicle.id.toString()
            )
        }

        stops.forEach { stop ->
            Marker(
                state = MarkerState(position = LatLng(stop.latitude, stop.longitude)),
                title = stop.name
            )
        }
    }
}
