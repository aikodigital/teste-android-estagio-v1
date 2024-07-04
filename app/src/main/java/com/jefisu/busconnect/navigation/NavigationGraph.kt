package com.jefisu.busconnect.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jefisu.bus_stops.busStopsScreen
import com.jefisu.bus_stops.navigateToBusStopsScreen
import com.jefisu.home.homeScreen

@Composable
fun NavigationGraph(
    startRoute: com.jefisu.common.Route
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startRoute
    ) {
        homeScreen(
            navigateToDetailBusStop = navController::navigateToBusStopsScreen
        )
        busStopsScreen(
            onBackClick = navController::navigateUp
        )
    }
}