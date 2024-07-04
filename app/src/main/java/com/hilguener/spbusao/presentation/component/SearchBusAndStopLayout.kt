package com.hilguener.spbusao.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchBusAndStopLayout(
    busLine: String,
    onBusLineChange: (String) -> Unit,
    busStopLocation: String,
    onBusStopLocationChange: (String) -> Unit,
    busStopLine: String,
    onBusStopLineChange: (String) -> Unit,
    onSearch: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Onibus",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.displaySmall
        )
        CustomOutlinedTextField(
            value = busLine,
            onValueChange = onBusLineChange,
            label = "Linha",
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            ),
            keyboardActions = KeyboardActions(onSearch = { onSearch() }),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Paradas",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.displaySmall
        )
        CustomOutlinedTextField(
            value = busStopLocation,
            onValueChange = onBusStopLocationChange,
            label = "Localização",
            modifier = Modifier.fillMaxWidth(),
            enabled = busStopLine.isEmpty(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search,
            ),
            keyboardActions = KeyboardActions(onSearch = {
                keyboardController?.hide()
                onSearch()
            }),
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomOutlinedTextField(
            value = busStopLine,
            onValueChange = onBusStopLineChange,
            label = "Linha",
            modifier = Modifier.fillMaxWidth(),
            enabled = busStopLocation.isEmpty(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Search,
            ),
            keyboardActions = KeyboardActions(onSearch = {
                keyboardController?.hide()
                onSearch()
            }),
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}