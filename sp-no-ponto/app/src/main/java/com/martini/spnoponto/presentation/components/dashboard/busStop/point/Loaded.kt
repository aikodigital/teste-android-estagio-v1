package com.martini.spnoponto.presentation.components.dashboard.busStop.point

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationSearching
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.martini.spnoponto.R
import com.martini.spnoponto.domain.entities.busStopForecast.BusStopPoint

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BusStopMapPointLoaded(
    busStopPoint: BusStopPoint,
) {
    val busses = busStopPoint.lines.flatMap { it.busses }

    if (busses.isEmpty()) {
        BusStopMapPointEmpty(stringResource(R.string.no_bus_for_this_stop))
        return
    }

    Column(
        modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth()
    ) {
        ListItem(
            icon = {
                Icon(
                    Icons.Filled.LocationSearching,
                    contentDescription = stringResource(R.string.bus_stop)
                )
            },
            text = { Text(busStopPoint.name) },
            secondaryText = {
                BusStopMapLastUpdated()
            }
        )
        Divider()
        LazyColumn {
            items(busses) { bus ->
                BusStopMapPointLoadedBusTile(bus)
            }
        }
    }
}