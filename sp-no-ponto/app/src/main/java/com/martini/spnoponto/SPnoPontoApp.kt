package com.martini.spnoponto

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.martini.spnoponto.domain.usecases.NavigationAction
import com.martini.spnoponto.presentation.components.globalViewModels.NavigationViewModel
import com.martini.spnoponto.presentation.screens.DashboardScreen
import com.martini.spnoponto.presentation.screens.LineDetailsScreen
import com.martini.spnoponto.presentation.screens.Screen
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@ExperimentalMaterialApi
@Composable
fun SPnoPontoApp(
    navigationViewModel: NavigationViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    val systemUiController = rememberSystemUiController()

    val systemColor = if (MaterialTheme.colors.isLight) {
        MaterialTheme.colors.primary
    } else {
        MaterialTheme.colors.surface
    }

    SideEffect {
        systemUiController.setSystemBarsColor(
            systemColor
        )
    }

    fun navigateToLineDetails(linha: String) {
        navController.navigate(Screen.LineDetails.passLine(linha))
    }

    fun goBack() {
        navController.popBackStack()
    }

    LaunchedEffect(Unit) {
        navigationViewModel.listen()
            .onEach {
                when (it) {
                    is NavigationAction.NavigateToLineDetails -> {
                        navigateToLineDetails(it.line)
                    }
                    is NavigationAction.GoBack -> goBack()
                    is NavigationAction.Failure -> {}
                }
            }
            .launchIn(this)
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route
    ) {
        composable(
            route = Screen.Dashboard.route
        ) {
            DashboardScreen()
        }
        composable(
            route = Screen.LineDetails.route
        ) {
            LineDetailsScreen()
        }
    }
}