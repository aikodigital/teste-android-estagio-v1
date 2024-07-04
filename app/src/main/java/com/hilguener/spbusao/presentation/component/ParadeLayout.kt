package com.hilguener.spbusao.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hilguener.spbusao.domain.model.Parades
import com.hilguener.spbusao.domain.model.PrevLine
import com.hilguener.spbusao.presentation.map.MapsViewModel

@Composable
fun ParadeLayout(idParadeOrVehicle: Parades, viewModel: MapsViewModel) {
    val parade by remember { mutableStateOf(viewModel.getSelectedParade(idParadeOrVehicle.codeOfParade.toString())) }

    val arrivalLines by viewModel.listOfArrivalLines.collectAsState(initial = emptyList<PrevLine>())

    val nameOfParade = parade?.nameOfParade ?: "Informação indisponível"
    val addressOfParade = parade?.addressOfParade.orEmpty()

    LaunchedEffect(parade) {
        if (parade != null) {
            viewModel.getArrivalVehicles(parade!!.codeOfParade)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = nameOfParade,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = addressOfParade,
            fontSize = 16.sp,
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(arrivalLines ?: emptyList()) { line ->
                ArrivalLinesItem(arrivalLine = line)
            }
        }
    }
}
