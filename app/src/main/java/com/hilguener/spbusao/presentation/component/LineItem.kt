package com.hilguener.spbusao.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hilguener.spbusao.R
import com.hilguener.spbusao.domain.model.Lines

@Composable
fun LineItem(line: Lines) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = line.codeOfLine.toString(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 20.dp),
                    style = MaterialTheme.typography.displaySmall,
                )
                Text(
                    text = line.signOfLineDirectionPrincipal,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Image(
                    painter = painterResource(id = R.drawable.baseline_arrow_right_alt_24),
                    contentDescription = "Arrow",
                    modifier = Modifier.padding(8.dp),
                )
                Text(
                    text = line.signOfLineDirectionSecondary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Circular? ${if (line.worksInCircleMode) "Sim" else "Não"}",
                modifier = Modifier.padding(start = 8.dp),
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = "Sentido: ${if (line.directionOfWorks == 1) "Principal para secundário" else "Secundário para principal"}",
                modifier = Modifier.padding(start = 8.dp),
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = stringResource(
                    R.string.txt_sign,
                    line.firstPartOfSignLine,
                    line.secondPartOfSignLine.toString(),
                ),
                modifier = Modifier.padding(bottom = 16.dp, start = 8.dp),
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}