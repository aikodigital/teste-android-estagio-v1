package com.aiko.aikospvision.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aiko.aikospvision.ui.screens_app.bus_lines.ScreenBusLines
import com.aiko.aikospvision.ui.screens_app.home.ScreenHome
import com.aiko.aikospvision.ui.screens_app.stops.ScreenStops
import com.aiko.aikospvision.ui.screens_app.veicle_position.ScreenVehiclePosition
import com.aiko.aikospvision.ui.theme.AikoSPVisionTheme

@Composable
fun NavigationHost() {
    val navController = rememberNavController()
    AikoSPVisionTheme {
        NavHost(navController = navController, startDestination = "home/home_screen") {
            composable(route = "home/home_screen") { ScreenHome(navController) }

            composable(
                route = "home/stops_screen/{stopBusId}/{stopBusName}",
                arguments = listOf(
                    navArgument("stopBusId") { type = NavType.LongType },
                    navArgument("stopBusName") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val stopBusId = backStackEntry.arguments?.getLong("stopBusId") ?: null
                val stopBusName = backStackEntry.arguments?.getString("stopBusName") ?: ""
                ScreenStops(navController = navController, stopBusId = stopBusId, stopBusName = stopBusName)
            }

            composable(
                route = "home/vehicle_screen/" +
                        "{vehicleOrigin}/" +
                        "{vehicleDestination}/" +
                        "{vehicleCompleteSign}/" +
                        "{vehicleLineSense}/" +
                        "{vehicleLatitude}/" +
                        "{vehicleLongitude}",
                arguments = listOf(
                    navArgument("vehicleOrigin") { type = NavType.StringType },
                    navArgument("vehicleDestination") { type = NavType.StringType },
                    navArgument("vehicleCompleteSign") { type = NavType.StringType },
                    navArgument("vehicleLineSense") { type = NavType.StringType },
                    navArgument("vehicleLatitude") { type = NavType.StringType },
                    navArgument("vehicleLongitude") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val vehicleOrigin = backStackEntry.arguments?.getString("vehicleOrigin") ?: ""
                val vehicleDestination = backStackEntry.arguments?.getString("vehicleDestination") ?: ""
                val vehicleCompleteSign = backStackEntry.arguments?.getString("vehicleCompleteSign") ?: ""
                val vehicleLineSense = backStackEntry.arguments?.getString("vehicleLineSense") ?: ""
                val vehicleLatitude = backStackEntry.arguments?.getString("vehicleLatitude") ?: ""
                val vehicleLongitude = backStackEntry.arguments?.getString("vehicleLongitude") ?: ""
                ScreenVehiclePosition(
                    navController = navController,
                    vehicleOrigin = vehicleOrigin,
                    vehicleDestination = vehicleDestination,
                    vehicleCompleteSign = vehicleCompleteSign,
                    vehicleLineSense = vehicleLineSense,
                    latitude = vehicleLatitude.toDoubleOrNull(),
                    longitude = vehicleLongitude.toDoubleOrNull()
                )
            }

            composable(route = "lines/lines_screen") { ScreenBusLines(navController) }

        }
    }
}