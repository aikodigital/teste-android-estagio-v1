package com.aiko.bus.ui


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material3.Icon

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aiko.bus.ui.screens.BusLaneScreen
import com.aiko.bus.ui.screens.BusLanesScreen
import com.aiko.bus.ui.viewmodels.LinesViewModel
import com.aiko.bus.ui.viewmodels.StopViewModel
import com.aiko.bus.ui.viewmodels.VehiclesViewModel
import com.aiko.bus.ui.screens.LineScreen
import com.aiko.bus.ui.screens.LinesScreen
import com.aiko.bus.ui.viewmodels.BusLaneViewModel

val linesViewModel = LinesViewModel()
val stopViewModel = StopViewModel()
val vehiclesViewModel = VehiclesViewModel()
val busLaneViewModel = BusLaneViewModel()

sealed class Screen(val route: String, val label: String) {
    data object ListLines : Screen("lines", "Linhas")
    data object ListBusLanes : Screen("bus_lanes", "Corredores")
}

val screens = listOf(Screen.ListLines, Screen.ListBusLanes)
val show = listOf(Screen.ListLines.route, Screen.ListBusLanes.route)

@Composable
fun Navigation() {

    val navController = rememberNavController()
    val currentScreen by navController.currentBackStackEntryAsState()


    Box(modifier = Modifier.fillMaxWidth()) {
        NavHost(navController = navController, startDestination = "lines") {
            composable("lines") {
                LinesScreen(linesViewModel = linesViewModel) { lineId ->
                    navController.navigate("lines/$lineId")
                }
            }

            composable("lines/{lineId}") { backStackEntry ->
                val lineId = backStackEntry.arguments?.getString("lineId")?.toIntOrNull()

                LineScreen(
                    navController = navController,
                    vehiclesViewModel = vehiclesViewModel,
                    stopViewModel = stopViewModel,
                    line = linesViewModel.getLineById(lineId!!)
                )
            }

            composable("bus_lanes") {
                BusLanesScreen(busLaneViewModel = busLaneViewModel) { busLaneId ->
                    navController.navigate("bus_lanes/$busLaneId")
                }
            }

            composable("bus_lanes/{busLaneId}") { backStackEntry ->
                val busLaneId = backStackEntry.arguments?.getString("busLaneId")?.toIntOrNull()
                BusLaneScreen(
                    stopViewModel = stopViewModel,
                    navController = navController,
                    busLane = busLaneId!!
                )
            }
        }

        if (currentScreen?.destination?.route in show) {
            NavigationBar(
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                screens.forEach { screen ->
                    NavigationBarItem(
                        label = { Text(screen.label) },
                        selected = currentScreen?.destination?.route == screen.route,
                        icon = { Icon(Icons.TwoTone.Home, contentDescription = null) },
                        onClick = { navController.navigate(screen.route) }
                    )
                }
            }
        }

    }
}