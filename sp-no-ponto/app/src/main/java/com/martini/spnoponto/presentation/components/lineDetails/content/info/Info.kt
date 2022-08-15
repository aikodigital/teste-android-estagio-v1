package com.martini.spnoponto.presentation.components.lineDetails.content.info

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.martini.spnoponto.R

@ExperimentalMaterialApi
@Composable
fun LineDetailsMapInfo() {
    Surface(
        color = MaterialTheme.colors.primary
    ) {
        ListItem(
            icon = {
                Icon(
                    Icons.Filled.Info,
                    contentDescription = stringResource(R.string.Information),
                )
            },
            text = {
                Text(stringResource(R.string.info_updated_each_30_seconds))
            }
        )
    }
}