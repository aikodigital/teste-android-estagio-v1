package com.jefisu.bus_stops

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jefisu.bus_stops.components.ArrivePredictionInfo
import com.jefisu.bus_stops.components.BusStopsList
import com.jefisu.bus_stops.components.MapScreen
import com.jefisu.common.network.ConnectivityObserver
import com.jefisu.ui.components.StandardScreen
import com.jefisu.ui.theme.AppTheme
import com.jefisu.ui.theme.PrimaryDark
import com.jefisu.ui.theme.PrimaryLight
import org.koin.compose.koinInject

@Composable
fun BusStopsScreen(
    state: BusStopsState,
    onAction: (BusStopsAction) -> Unit,
    onBackClick: () -> Unit
) {
    val status by koinInject<ConnectivityObserver>()
        .observe()
        .collectAsStateWithLifecycle(
            initialValue = ConnectivityObserver.Status.Unavailable
        )

    StandardScreen(
        error = state.error,
        isLoading = false,
        isOffline = status != ConnectivityObserver.Status.Available,
        fixedContent = {
            BusStopsTopBar(
                onBackClick = onBackClick,
                state = state,
                onAction = onAction
            )
        },
        content = {
            Box {
                MapScreen(
                    state = state,
                    modifier = Modifier
                        .graphicsLayer {
                            alpha = if (state.showMapView) 1f else 0f
                        }
                        .zIndex(if (state.showMapView) 1f else 0f)
                )
                Column(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .padding(top = AppTheme.spacing.small)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = AppTheme.spacing.medium)
                    ) {
                        Text(
                            text = stringResource(R.string.choos_bus_stop),
                            style = AppTheme.typography.header2,
                            fontWeight = FontWeight.Medium,
                            color = PrimaryDark,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(AppTheme.spacing.small))
                        Switch(
                            checked = state.isStartDirection,
                            onCheckedChange = { onAction(BusStopsAction.ChangeDirectionStops) },
                            thumbContent = {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                                    contentDescription = null,
                                    modifier = Modifier.rotate(
                                        if (state.isStartDirection) 0f else 180f
                                    )
                                )
                            },
                            colors = SwitchDefaults.colors(
                                checkedIconColor = PrimaryDark,
                                uncheckedIconColor = PrimaryLight,
                                checkedTrackColor = PrimaryDark,
                                uncheckedTrackColor = Color.Transparent,
                                uncheckedBorderColor = PrimaryDark,
                                uncheckedThumbColor = PrimaryDark,
                                checkedThumbColor = PrimaryLight
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(AppTheme.spacing.medium))
                    BusStopsList(
                        stops = state.stops,
                        onSelectBusStop = { onAction(BusStopsAction.SelectBusStop(it)) },
                    )
                }
                ArrivePredictionInfo(
                    predictionStop = state.predictionStop,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .navigationBarsPadding()
                )
            }
        }
    )
}

@Composable
private fun BusStopsTopBar(
    onBackClick: () -> Unit,
    state: BusStopsState,
    onAction: (BusStopsAction) -> Unit
) {
    CompositionLocalProvider(
        LocalContentColor provides PrimaryDark
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .background(Color.White)
                .padding(6.dp)
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "Back",
                    modifier = Modifier.size(35.dp)
                )
            }
            Text(
                text = stringResource(R.string.line, state.lineCode),
                style = AppTheme.typography.header2,
                fontWeight = FontWeight.Medium,
                overflow = TextOverflow.Ellipsis
            )
            IconButton(onClick = { onAction(BusStopsAction.OpenMapView) }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_location),
                    contentDescription = "Open map view",
                    modifier = Modifier.size(35.dp),
                    tint = if (state.showMapView) PrimaryDark else PrimaryDark.copy(0.5f)
                )
            }
        }
    }
}