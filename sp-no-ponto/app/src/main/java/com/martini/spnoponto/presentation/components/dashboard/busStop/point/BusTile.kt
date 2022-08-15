package com.martini.spnoponto.presentation.components.dashboard.busStop.point

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BusAlert
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.martini.spnoponto.R
import com.martini.spnoponto.domain.entities.busStopForecast.BusStopPointBus

@ExperimentalMaterialApi
@Composable
fun BusStopMapPointLoadedBusTile(
    bus: BusStopPointBus
) {
    ListItem(
        icon = {
            Icon(Icons.Filled.BusAlert, contentDescription = stringResource(R.string.Bus))
        },
        text = { Text(bus.prefix) },
        secondaryText = { Text(stringResource(R.string.arrives_at, bus.forecast)) }
    )
}