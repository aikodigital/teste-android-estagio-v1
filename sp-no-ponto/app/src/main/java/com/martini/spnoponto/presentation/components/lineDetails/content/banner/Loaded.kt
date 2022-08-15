package com.martini.spnoponto.presentation.components.lineDetails.content.banner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.martini.spnoponto.R
import com.martini.spnoponto.domain.entities.lineSearch.Linha

@Composable
fun LineDetailsLoaded(linha: Linha) {

    val way = if (linha.way == 1) {
        stringResource(R.string.secondary_terminal)
    } else {
        stringResource(R.string.primary_terminal)
    }

    val colors = TextFieldDefaults.textFieldColors(
        disabledLabelColor = MaterialTheme.colors.primary,
        textColor = if (MaterialTheme.colors.isLight) {
            Color.Black
        } else {
            Color.White
        }
    )

    val modifier = Modifier.fillMaxWidth()

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Text(
            stringResource(R.string.line_information),
            textAlign = TextAlign.Center,
            style = TextStyle(
                color = MaterialTheme.colors.primary
            )
        )
        TextField(
            modifier = modifier,
            enabled = false,
            onValueChange = {},
            value = linha.code.toString(),
            label = { Text(stringResource(R.string.Code)) },
            colors = colors
        )
        TextField(
            modifier = modifier,
            enabled = false,
            onValueChange = {},
            value = if (linha.circular) stringResource(R.string.Yes) else stringResource(R.string.No),
            label = { Text(stringResource(R.string.Circular)) },
            colors = colors
        )
        TextField(
            modifier = modifier,
            enabled = false,
            onValueChange = {},
            value = "${linha.firstSign}-${linha.secondSign}",
            label = { Text(stringResource(R.string.Sign)) },
            colors = colors
        )
        TextField(
            modifier = modifier,
            enabled = false,
            onValueChange = {},
            value = way,
            label = { Text(stringResource(R.string.Way)) },
            colors = colors
        )
        TextField(
            modifier = modifier,
            enabled = false,
            onValueChange = {},
            value = stringResource(
                R.string.line_description,
                linha.descriptionPrimary,
                linha.descriptionSecondary
            ),
            label = { Text(stringResource(R.string.Description)) },
            colors = colors
        )
    }
}