package com.aiko.aikospvision.ui.screens_app.home

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aiko.aikospvision.gmaps.MapComponent
import com.aiko.aikospvision.gmaps.convertParadasToMapCordinators
import com.aiko.aikospvision.gmaps.requestLocationUpdates
import com.aiko.aikospvision.ui.componentes.IndeterminateCircularIndicator
import com.aiko.aikospvision.ui.componentes.NavigationBar
import com.aiko.aikospvision.ui.componentes.Scaffold
import com.aiko.aikospvision.ui.componentes.SearchInputField
import com.aiko.aikospvision.ui.theme.AikoSPVisionTheme
import com.alabia.manager.ui.state.TaskState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.maps.model.LatLng
import org.koin.androidx.compose.getViewModel

@Composable
private fun getModel(): ViewModelHome {
    return if (LocalInspectionMode.current) {
        MockViewModelHome()
    } else {
        getViewModel<ViewModelHomeImpl>()
    }
}

@Composable
fun ScreenHome(navController: NavController, viewModel: ViewModelHome = getViewModel()) {
    val context = LocalContext.current.applicationContext
    val state by viewModel.state.collectAsState()
    val taskState by viewModel.taskState.collectAsState()
    var currentLocation by remember { mutableStateOf<LatLng?>(null) }

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = Color.Transparent, darkIcons = true)
    systemUiController.setNavigationBarColor(MaterialTheme.colorScheme.surfaceVariant)

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            requestLocationUpdates(context) { location ->
                currentLocation = LatLng(location.latitude, location.longitude)
            }
        }
    }

    LaunchedEffect(Unit) {
        locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    Column(
        modifier = Modifier
            .navigationBarsPadding()
            .statusBarsPadding()
    ) {
        Scaffold(
            titleTopBar = "HOME",
            navigationUp = navController,
            modifier = Modifier,
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
                        Row(modifier = Modifier.padding(16.dp)) {
                            SearchInputField(
                                value = state.searchText ?: "",
                                onValueChange = { value ->
                                    viewModel.onEvent(
                                        HomeFormEvent.SearchChanged(
                                            value
                                        )
                                    )
                                },
                                onSearchClick = {
                                    viewModel.onEvent(HomeFormEvent.Submit)
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        MapComponent(
                            convertParadasToMapCordinators(state.listStopped),
                            navController,
                            onClickIsOn = true
                        )
                        if (taskState is TaskState.Loading) {
                            IndeterminateCircularIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                        } else {
                            if (viewModel.state.value.listStopped.isNotEmpty()) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .align(Alignment.CenterHorizontally)
                                        .padding(16.dp)
                                ) {
                                    Row {
                                        Column {
                                            LazyColumn(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .clip(RoundedCornerShape(16.dp))
                                            ) {
                                                if (viewModel.state.value.listStopped.isNotEmpty()) {
                                                    item {
                                                        Box(
                                                            modifier = Modifier
                                                                .padding(16.dp)
                                                                .fillMaxWidth()
                                                        ) {
                                                        }
                                                    }
                                                } else {
                                                    item { Row { Text(text = "Sem itens") } }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}

@Preview(name = "PHONE", showBackground = true, showSystemUi = true, device = Devices.PHONE)
@Composable
fun ScreenHomeManagerPreview() {
    AikoSPVisionTheme {
        ScreenHome(
            navController = NavController(LocalContext.current),
            viewModel = MockViewModelHome()
        )
    }
}