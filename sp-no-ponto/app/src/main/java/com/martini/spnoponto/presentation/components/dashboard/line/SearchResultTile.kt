package com.martini.spnoponto.presentation.components.dashboard.line

import androidx.compose.foundation.clickable
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.martini.spnoponto.R
import com.martini.spnoponto.domain.entities.lineSearch.Linha

@ExperimentalMaterialApi
@Composable
fun SearchResultTile(
    linha: Linha,
    onClick: (Linha) -> Unit
) {

    val way = if (linha.way == 1) {
        stringResource(R.string.Secondary)
    } else {
        stringResource(R.string.Primary)
    }

    ListItem(
        modifier = Modifier.clickable { onClick(linha) },
        icon = { Text("${linha.firstSign}-${linha.secondSign}") },
        text = { Text("${linha.descriptionPrimary} - $way") },
        secondaryText = { Text(linha.descriptionSecondary) },
        trailing = {
            Icon(
                Icons.Filled.MoreVert,
                contentDescription = stringResource(R.string.more_information)
            )
        }
    )
}