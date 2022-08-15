package com.martini.spnoponto.presentation.components.dashboard.line

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.martini.spnoponto.R

@Composable
fun SearchLineInitial() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            Icons.Filled.Search,
            contentDescription = stringResource(R.string.Search),
            modifier = Modifier.size(80.dp)
        )
        Text(
            text = stringResource(R.string.everything_empty),
        )
        Text(text = stringResource(R.string.search_for_a_line))
    }
}