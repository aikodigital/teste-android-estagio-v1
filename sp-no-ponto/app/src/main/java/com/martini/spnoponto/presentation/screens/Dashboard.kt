package com.martini.spnoponto.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.martini.spnoponto.presentation.components.dashboard.DashboardTabs
import com.martini.spnoponto.presentation.components.dashboard.appbar.DashboardAppBar
import com.martini.spnoponto.presentation.components.dashboard.busStop.BusStopMap
import com.martini.spnoponto.presentation.components.dashboard.line.SearchResultList

@ExperimentalMaterialApi
@Composable
fun DashboardScreen() {

    var tabIndex by remember {
        mutableStateOf(0)
    }

    fun onChange(tab: Int) {
        tabIndex = tab
    }

    Scaffold(
        topBar = { DashboardAppBar(tabIndex) },
        bottomBar = {
            DashboardTabs(
                tabIndex = tabIndex,
                onChange = { onChange(it) }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                when(tabIndex) {
                    0 -> SearchResultList()
                    else -> BusStopMap()
                }
            }
        }
    )
}