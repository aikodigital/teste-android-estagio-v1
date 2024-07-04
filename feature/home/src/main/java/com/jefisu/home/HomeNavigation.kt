package com.jefisu.home

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jefisu.common.Route
import com.jefisu.domain.model.Line
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object HomeScreenRoute : Route

fun NavGraphBuilder.homeScreen(
    navigateToDetailBusStop: (Line) -> Unit
) = composable<HomeScreenRoute> {
    val viewModel = koinViewModel<HomeViewModel>()
    val state = viewModel.state

    LaunchedEffect(key1 = state) {
        viewModel.navigateAction.collect { line ->
            navigateToDetailBusStop(line)
        }
    }

    HomeScreen(
        state = state,
        onAction = viewModel::onAction
    )
}