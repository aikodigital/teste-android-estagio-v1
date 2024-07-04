package com.jefisu.bus_stops.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.jefisu.bus_stops.R
import com.jefisu.common.formatDateToHour
import com.jefisu.domain.model.PredictionStop
import com.jefisu.ui.theme.AppTheme
import com.jefisu.ui.theme.BackgroundPrimaryLight
import com.jefisu.ui.theme.PrimaryDark
import com.jefisu.ui.theme.TextPrimaryLight

@Composable
fun ArrivePredictionInfo(
    predictionStop: PredictionStop?,
    modifier: Modifier = Modifier,
) {
    if (predictionStop != null) {
        Surface(
            color = BackgroundPrimaryLight,
            shape = RoundedCornerShape(20.dp, 20.dp),
            modifier = modifier
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .heightIn(max = 300.dp)
                    .padding(AppTheme.spacing.extraSmall)
                    .animateContentSize(animationSpec = tween(300))
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(R.string.next_to_arrive),
                        style = AppTheme.typography.header2,
                        color = PrimaryDark,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(R.string.updated, ""))
                            withStyle(
                                style = AppTheme.typography.header3
                                    .copy(color = PrimaryDark)
                                    .toSpanStyle()
                            ) {
                                append(predictionStop.lastUpdate.replace(':', 'h'))
                            }
                        },
                        style = AppTheme.typography.header3,
                        color = TextPrimaryLight,
                        textAlign = TextAlign.End,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.height(1.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    items(predictionStop.predictionStop.lines) { line ->
                        val vehicles = line.vehicles.take(1)
                        Column {
                            vehicles.fastForEach { vehicle ->
                                val hourFormatted = remember {
                                    formatDateToHour(vehicle.lastUpdate)
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Canvas(modifier = Modifier.size(4.dp)) {
                                        drawCircle(color = PrimaryDark)
                                    }
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(text = line.code)
                                    Spacer(modifier = Modifier.weight(1f))
                                    HorizontalDivider(
                                        color = PrimaryDark,
                                        modifier = Modifier.width(10.dp)
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text(text = hourFormatted, color = PrimaryDark)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

