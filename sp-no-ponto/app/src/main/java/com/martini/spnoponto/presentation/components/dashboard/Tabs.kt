package com.martini.spnoponto.presentation.components.dashboard

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.martini.spnoponto.R

@Composable
fun DashboardTabs(
    tabIndex: Int,
    onChange: (Int) -> Unit
) {
    val tabList = listOf(
        stringResource(R.string.tabs_line),
        stringResource(R.string.tabs_bus_stops),
    )

    val icons = listOf(
        Icons.Filled.List,
        Icons.Filled.AirlineStops,
    )

    TabRow(
        selectedTabIndex = tabIndex,
        tabs = {
            tabList.forEachIndexed { index, tab ->
                Tab(
                    selected = tabIndex == index,
                    onClick = {
                        onChange(index)
                    },
                    icon = { Icon(icons[index], contentDescription = tab) },
                    text = {
                        Text(
                            text = tab,
                            style = TextStyle(
                                fontSize = 13.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                )
            }
        })
}