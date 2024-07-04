package com.jefisu.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jefisu.domain.model.Line
import com.jefisu.home.HomeAction
import com.jefisu.ui.components.DynamicVerticalList
import com.jefisu.ui.theme.AppTheme
import com.jefisu.ui.theme.PrimaryDark
import com.jefisu.ui.theme.StarColor
import com.jefisu.ui.theme.TextPrimary
import com.jefisu.ui.theme.TextPrimaryLight
import com.jefisu.common.R as CommonRes

@Composable
fun BusDirectionsList(
    busLines: List<Line>,
    searchBusNumber: String,
    onAction: (HomeAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()
    val busLinesFiltered by remember(searchBusNumber) {
        derivedStateOf {
            busLines.filter {
                it.run {
                    code.contains(searchBusNumber, ignoreCase = true)
                            || origin.contains(searchBusNumber, ignoreCase = true)
                            || destination.contains(searchBusNumber, ignoreCase = true)
                }
            }
        }
    }

    LaunchedEffect(key1 = searchBusNumber) {
        if (searchBusNumber.isBlank()) {
            lazyListState.scrollToItem(0)
        }
    }

    DynamicVerticalList(
        modifier = modifier.fillMaxSize(),
        lazyListState = lazyListState,
        spacingBetweenItems = AppTheme.spacing.medium,
        contentPadding = PaddingValues(horizontal = AppTheme.spacing.medium),
        items = busLinesFiltered,
        itemContent = {_,  busLine ->
            BusDirectionsItem(
                busLine = busLine,
                onClick = {
                    onAction(HomeAction.NavigateToBusStops(busLine))
                }
            )
        }
    )
}

@Composable
private fun BusDirectionsItem(
    busLine: Line,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = Color.White

    Column(
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.small),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(Shapes().small)
                .background(backgroundColor)
                .padding(
                    vertical = 4.dp,
                    horizontal = AppTheme.spacing.extraSmall
                )
        ) {
            Text(
                text = busLine.code,
                style = AppTheme.typography.header2,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryDark
            )
            Icon(
                painter = painterResource(id = CommonRes.drawable.ic_star_outlined),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = StarColor
            )
        }
        Spacer(modifier = Modifier.height(AppTheme.spacing.extraSmall))
        Column(
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.small),
            modifier = Modifier
                .clip(Shapes().small)
                .background(backgroundColor)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(),
                    onClick = onClick
                )
                .padding(AppTheme.spacing.extraSmall)
        ) {
            StopNameItem(
                stopName = busLine.origin,
                isStartDirection = true
            )
            HorizontalDivider(
                color = TextPrimaryLight,
                modifier = Modifier.padding(vertical = 2.dp)
            )
            StopNameItem(
                stopName = busLine.destination,
                isStartDirection = false
            )
        }
    }
}

@Composable
private fun StopNameItem(
    stopName: String,
    isStartDirection: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.small),
        modifier = modifier
    ) {
        Text(
            text = stopName,
            color = TextPrimary,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
            contentDescription = null,
            tint = PrimaryDark,
            modifier = Modifier
                .size(24.dp)
                .rotate(
                    if (isStartDirection) 0f else 180f
                )
        )
    }
}