package com.jefisu.bus_stops.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.jefisu.bus_stops.R
import com.jefisu.bus_stops.util.calculateDistance
import com.jefisu.domain.model.BusStop
import com.jefisu.ui.components.DynamicVerticalList
import com.jefisu.ui.theme.AppTheme
import com.jefisu.ui.theme.PrimaryDark
import com.jefisu.ui.theme.PrimaryLight
import com.jefisu.ui.theme.TextPrimary

@Composable
fun BusStopsList(
    stops: List<BusStop>,
    onSelectBusStop: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val lazyListState = rememberLazyListState()
    var selectedStopIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    DynamicVerticalList(
        modifier = modifier,
        lazyListState = lazyListState,
        spacingBetweenItems = AppTheme.spacing.extraSmall,
        contentPadding = PaddingValues(horizontal = AppTheme.spacing.medium),
        items = stops,
        itemContent = { index, stop ->
            val startStop = stops.first()
            val distance = remember {
                calculateDistance(
                    startLat = startStop.latitude,
                    startLon = startStop.longitude,
                    endLat = stop.latitude,
                    endLon = stop.longitude
                )
            }
            BusStopItem(
                stop = stop,
                isMarkedStop = index == selectedStopIndex,
                distance = distance,
                onClickSelectStop = {
                    selectedStopIndex = index
                    onSelectBusStop(selectedStopIndex)
                }
            )
        }
    )
}

@Composable
private fun BusStopItem(
    stop: BusStop,
    distance: Double,
    isMarkedStop: Boolean,
    onClickSelectStop: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            onClick = onClickSelectStop,
            shape = RoundedCornerShape(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(28.dp)
                    .height(10.dp)
                    .background(
                        shape = RoundedCornerShape(4.dp),
                        color = if (isMarkedStop) PrimaryDark else PrimaryLight
                    )
            )
        }
        Spacer(modifier = Modifier.width(AppTheme.spacing.medium))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.extraSmall),
            modifier = Modifier
                .weight(1f)
                .clip(Shapes().medium)
                .background(Color.White)
                .padding(
                    horizontal = AppTheme.spacing.medium,
                    vertical = AppTheme.spacing.extraSmall
                )
        ) {
            Text(
                text = "%.2f KM".format(distance),
                style = AppTheme.typography.header2,
                color = PrimaryDark,
                fontWeight = FontWeight.Medium,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = stop.address,
                color = TextPrimary,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_forward),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}