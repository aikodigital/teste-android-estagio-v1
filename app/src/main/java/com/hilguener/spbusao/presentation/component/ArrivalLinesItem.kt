package com.hilguener.spbusao.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hilguener.spbusao.domain.model.PrevLine

@Composable
fun ArrivalLinesItem(arrivalLine: PrevLine) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = arrivalLine.signOfLine,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = arrivalLine.singOfOriginOfLine,
                fontSize = 16.sp,
                style = MaterialTheme.typography.displaySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = arrivalLine.signOfDestinyOfLine,
                fontSize = 16.sp,
                style = MaterialTheme.typography.displaySmall
            )
        }
    }
    Divider(color = Color.Gray, thickness = 1.dp)
    Spacer(modifier = Modifier.height(8.dp))
    arrivalLine.vehicles.forEach { vehicles ->
        PrevLineItem(vehicles)
    }


}