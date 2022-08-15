package com.martini.spnoponto.presentation.components.dashboard.appbar

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable

@ExperimentalMaterialApi
@Composable
fun DashboardAppBar(
    tabIndex: Int
) {
    if (tabIndex != 0) return

    TopAppBar(
        title = { DashboardSearchField() },
        actions = {
            DashboardAppBarFilter()
        }
    )
}