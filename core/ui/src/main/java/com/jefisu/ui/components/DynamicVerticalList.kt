package com.jefisu.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun <T> DynamicVerticalList(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    spacingBetweenItems: Dp = 0.dp,
    lazyListState: LazyListState,
    items: List<T>,
    itemContent: @Composable LazyItemScope.(Int, T) -> Unit
) {
    val showDivider by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemScrollOffset > 0
                    || lazyListState.isScrollInProgress
                    && lazyListState.canScrollBackward
        }
    }

    Column(modifier = modifier) {
        AnimatedVisibility(visible = showDivider) {
            HorizontalDivider()
        }
        LazyColumn(
            state = lazyListState,
            contentPadding = contentPadding,
            verticalArrangement = Arrangement.spacedBy(spacingBetweenItems)
        ) {
            itemsIndexed(
                items = items,
                key = { index, _ -> index }
            ) { index, item ->
                itemContent(index, item)
            }
        }
    }
}