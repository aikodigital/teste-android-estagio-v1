package com.hilguener.spbusao.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hilguener.spbusao.domain.model.PrevVehicle

@Composable
fun PrevLineItem(vehicles: PrevVehicle) {
    Column {
        Text(
            text = "Prefixo do veiculo: ${vehicles.prefixOfVehicle}",
            style = MaterialTheme.typography.bodySmall,
            fontSize = 16.sp,
        )
        Text(
            text = "Horário previsto de chegada: ${vehicles.expectedArrivalTime}",
            style = MaterialTheme.typography.bodySmall,
            fontSize = 16.sp,
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}