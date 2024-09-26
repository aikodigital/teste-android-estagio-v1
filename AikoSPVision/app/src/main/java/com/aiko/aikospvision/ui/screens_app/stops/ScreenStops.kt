package com.aiko.aikospvision.ui.screens_app.stops

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.twotone.DoubleArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.navigation.NavController
import com.aiko.aikospvision.ui.componentes.NavigationBar
import com.aiko.aikospvision.ui.componentes.Scaffold
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.androidx.compose.getViewModel


@Composable
private fun getModel(): ViewModelStops {
    return if (LocalInspectionMode.current) {
        MockViewModelStops()
    } else {
        getViewModel<ViewModelStopsImpl>()
    }
}

@Composable
fun ScreenStops(
    navController: NavController,
    viewModel: ViewModelStops = getViewModel(),
    stopBusId: Long? = null,
    stopBusName: String = ""
) {
    val state by viewModel.state.collectAsState()
    val taskState by viewModel.taskState.collectAsState()
    if (stopBusId != null) {
        Log.e(TAG, "paradaId $stopBusId")
        viewModel.onGetForecast(stopBusId)
    }
    if (stopBusName.isNotEmpty()) {
        state.destinationText
        viewModel.onEvent(StopsFormEvent.StateChanged(state.copy(destinationText = stopBusName)))
    }
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = Color.Transparent, darkIcons = true)
    systemUiController.setNavigationBarColor(MaterialTheme.colorScheme.surfaceVariant)

    Column(
        modifier = Modifier
            .navigationBarsPadding()
            .statusBarsPadding()
    ) {
        Scaffold(
            titleTopBar = "PARADAS".uppercase(),
            navigationUp = navController,
            modifier = Modifier,
            actions = {
                IconButton(onClick = {}, enabled = false) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = null,
                        tint = Color.Transparent
                    )
                }
            },
            showTopBar = true,
            showLogo = false,
            showButtonToReturn = true,
            showBottomBarNavigation = true,
            bottomNavigationBar = { NavigationBar(navController) },
            contentToUse = { paddingValue ->
                Box(modifier = Modifier.padding(paddingValue)) {
                    Column {
                        Row(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text =state.destinationText,
                                style = MaterialTheme.typography.headlineMedium,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }
                        Row{
                            if (state.forecast != null) {
                                val pontoParada = state.forecast!!.pontoParada
                                LazyColumn(modifier = Modifier.padding(16.dp)) {
                                    item{
                                        Text(
                                            text = "Previsão de Chegada",
                                            style = MaterialTheme.typography.headlineSmall,
                                            modifier = Modifier.padding(bottom = 8.dp)
                                        )
                                        Text(
                                            text = "Parada: ${pontoParada?.nomeParada ?: "Não disponível"}",
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = "Código da Parada: ${pontoParada?.codigoParada ?: "Não disponível"}",
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                        Spacer(modifier = Modifier.height(16.dp))
                                        pontoParada?.linhas?.forEach { linha ->
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(vertical = 8.dp)
                                                    .background(
                                                        Color.LightGray.copy(alpha = 0.2f),
                                                        RoundedCornerShape(8.dp)
                                                    )
                                                    .padding(8.dp)

                                            ) {
                                                Text(
                                                    text = "Linha: ${linha.letreiroCompleto ?: "Desconhecida"}",
                                                    style = MaterialTheme.typography.bodyLarge,
                                                    modifier = Modifier.padding(bottom = 4.dp)
                                                )
                                                Text(
                                                    text = "Origem: ${linha.origem ?: "N/A"} ➔ Destino: ${linha.destino ?: "N/A"}",
                                                    style = MaterialTheme.typography.bodySmall
                                                )
                                                Spacer(modifier = Modifier.height(8.dp))
                                                linha.veiculos?.forEach { veiculo ->
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .padding(vertical = 4.dp)
                                                            .clickable {
                                                                navController.navigate(
                                                                    "home/vehicle_screen/" +
                                                                            "${Uri.encode(linha.origem ?: "-")}/" +
                                                                            "${Uri.encode(linha.destino ?: "-")}/" +
                                                                            "${Uri.encode(linha.letreiroCompleto ?: "-")}/" +
                                                                            "${Uri.encode(linha.sentidoLinha.toString())}/" +
                                                                            "${veiculo.latitude ?: 0.0}/" +
                                                                            "${veiculo.longitude ?: 0.0}"
                                                                )
                                                            },
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Column(
                                                            modifier = Modifier.weight(1f)
                                                        ) {
                                                            Text(
                                                                text = "Veículo: ${veiculo.prefixoVeiculo ?: "Desconhecido"}",
                                                                style = MaterialTheme.typography.bodySmall
                                                            )
                                                            Text(
                                                                text = "Horário Previsto: ${veiculo.horarioPrevisto ?: "Não disponível"}",
                                                                style = MaterialTheme.typography.bodySmall
                                                            )
                                                            Text(
                                                                text = "Acessível: ${if (veiculo.acessivel == true) "Sim" else "Não"}",
                                                                style = MaterialTheme.typography.bodySmall
                                                            )
                                                        }

                                                        Icon(
                                                            imageVector = Icons.TwoTone.DoubleArrow,
                                                            contentDescription = "Arrow Forward",
                                                            modifier = Modifier.padding(start = 8.dp),
                                                            tint = Color.Gray
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                Text(
                                    text = "Sem previsão disponível",
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}