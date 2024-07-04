package com.jefisu.bus_stops

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jefisu.domain.model.Line
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
data class BusStopsScreenRoute(
    val lineId: Int,
    val lineCode: String
) : com.jefisu.common.Route

fun NavController.navigateToBusStopsScreen(line: Line) {
    val routeWithArgs = BusStopsScreenRoute(line.id, line.code)
    navigate(routeWithArgs)
}

fun NavGraphBuilder.busStopsScreen(
    onBackClick: () -> Unit
) = composable<BusStopsScreenRoute> {
    val viewModel = koinViewModel<BusStopsViewModel>()
    BusStopsScreen(
        state = viewModel.state,
        onAction = viewModel::onAction,
        onBackClick = onBackClick
    )
}