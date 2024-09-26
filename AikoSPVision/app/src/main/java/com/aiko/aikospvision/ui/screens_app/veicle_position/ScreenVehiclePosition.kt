package com.aiko.aikospvision.ui.screens_app.veicle_position

import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.aiko.aikospvision.gmaps.MapComponent
import com.aiko.aikospvision.gmaps.MapCordinator
import com.aiko.aikospvision.ui.componentes.NavigationBar
import com.aiko.aikospvision.ui.componentes.Scaffold
import com.alabia.manager.ui.state.TaskState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.androidx.compose.getViewModel

@Composable
fun ScreenVehiclePosition(
    navController: NavController,
    viewModel: ViewModelVehiclePosition = getViewModel(),
    vehicleOrigin: String = "",
    vehicleDestination: String = "",
    latitude: Double? = null,
    longitude: Double? = null,
    vehicleCompleteSign: String,
    vehicleLineSense: String
) {
    val state by viewModel.state.collectAsState()
    val taskState by viewModel.taskState.collectAsState()

    if (vehicleDestination.isNotEmpty()) {
        viewModel.onEvent(VehiclePositionEvent.SaveVehicleInformationEvent(
            latitude = latitude,
            longitude = longitude,
            vehicleDestination = vehicleDestination,
            vehicleOrigin = vehicleOrigin,
            vehicleCompleteSign = vehicleCompleteSign,
            vehicleLineSense = vehicleLineSense
        ))
    }

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = Color.Transparent, darkIcons = true)
    systemUiController.setNavigationBarColor(MaterialTheme.colorScheme.surfaceVariant)

    Column(
        modifier = Modifier
            .navigationBarsPadding()
            .statusBarsPadding()
    ) {
        Scaffold(
            titleTopBar = "Ultima posição do Veículo".uppercase(),
            navigationUp = navController,
            modifier = Modifier,
            showButtonToReturn = true,
            actions = {
                IconButton(onClick = {}, enabled = false) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = null,
                        tint = Color.Transparent
                    )
                }
            },
            showTopBar = true,
            showLogo = false,
            showBottomBarNavigation = true,
            bottomNavigationBar = { NavigationBar(navController) },
            contentToUse = { paddingValue ->
                Box(modifier = Modifier.padding(paddingValue)) {
                    Column {
                        Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
                            Text(
                                text = "Linha: ${state.vehicleLineSense ?: "Desconhecida"}",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        }
                        Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                            Text(
                                text = "Origem: ${state.vehicleOrigin ?: "N/A"} ➔ Destino: ${state.vehicleDestination ?: "N/A"}",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        }
                        Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                            Text(
                                text = "Letreiro: ${state.vehicleCompleteSign ?: "Desconhecida"}",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        }
                        MapComponent(
                            listOf(MapCordinator(
                                    latitude = state.latitude,
                                    longitude = state.longitude,
                                )), navController, onClickIsOn = false)
                    }
                }
            }
        )
    }
}