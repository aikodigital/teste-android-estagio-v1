package com.example.aikodigital

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.PermissionChecker
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aikodigital.components.Navigation
import com.example.aikodigital.gui.BusStopDetailsScreen
import com.example.aikodigital.gui.BusStopScreen
import com.example.aikodigital.gui.CorredorStopScreen
import com.example.aikodigital.gui.CorredorsScreen
import com.example.aikodigital.gui.HomeScreen
import com.example.aikodigital.gui.LinesScreen
import com.example.aikodigital.gui.MenuScreen
import com.example.aikodigital.model.LocationState
import com.example.aikodigital.model.LocationViewModel
import com.example.aikodigital.model.MyViewModel
import com.example.aikodigital.ui.theme.AikodigitalTheme

class MainActivity : ComponentActivity() {
    private val locationViewModel: LocationViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AikodigitalTheme {
                val locationState by locationViewModel.locationState.collectAsState()

                val requestPermissionLauncher = rememberLauncherForActivityResult(
                    ActivityResultContracts.RequestPermission()
                ) { isGranted: Boolean ->
                    locationViewModel.onPermissionResult(isGranted)
                }

                Scaffold(modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.background))
                ) { innerPadding ->
                    when (locationState) {
                        is LocationState.Loading -> {
                            LaunchedEffect(Unit) {
                                val permissionCheck = androidx.core.content.ContextCompat.checkSelfPermission(
                                    this@MainActivity,
                                    android.Manifest.permission.ACCESS_FINE_LOCATION
                                )

                                if (permissionCheck == PermissionChecker.PERMISSION_GRANTED) {
                                    locationViewModel.onPermissionResult(true)
                                } else {
                                    requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
                                }
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(colorResource(id = R.color.background))
                                    .padding(horizontal = 20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text= stringResource(id = R.string.location),
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = colorResource(id = R.color.fonts)
                                )
                            }
                        }
                        is LocationState.Success -> {
                            val location = locationState as LocationState.Success
                            Greeting(modifier = Modifier
                                .padding(innerPadding),
                                location.latitude, location.longitude)
                        }
                        is LocationState.Error -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(colorResource(id = R.color.background))
                                    .padding(horizontal = 20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ){
                                Text(
                                    text = stringResource(id = R.string.denied),
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = colorResource(id = R.color.fonts)
                                )

                            }
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun Greeting(modifier: Modifier, latitude: Double, longitude: Double) {
    val navController = rememberNavController()
    val currentRoute = remember { mutableStateOf("home") }
    val viewModel : MyViewModel = viewModel()

    navController.addOnDestinationChangedListener { _, destination, _ ->
        currentRoute.value = destination.route ?: "route1"
    }

    Scaffold(
        bottomBar = {
            when (currentRoute.value) {
                "home" -> {}
                else -> Navigation(navController = navController)
            }
        },
        modifier = modifier
    ){innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { HomeScreen(navController) }
            composable("menu"){ MenuScreen(navController = navController) }
            composable("lines"){ LinesScreen(navController = navController, viewModel) }
            composable("corredors"){ CorredorsScreen(navController = navController, viewModel) }
            composable("busStop"){ BusStopScreen(navController = navController, viewModel,latitude, longitude) }
            composable("busStopDetails"){ BusStopDetailsScreen(navController = navController, viewModel) }
            composable("corredorStop"){ CorredorStopScreen(navController = navController, viewModel, latitude, longitude) }

        }
    }
}


