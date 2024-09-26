package com.aiko.aikospvision.ui.screens_app.bus_lines

import androidx.compose.foundation.background
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
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aiko.aikospvision.ui.componentes.IndeterminateCircularIndicator
import com.aiko.aikospvision.ui.componentes.NavigationBar
import com.aiko.aikospvision.ui.componentes.Scaffold
import com.aiko.aikospvision.ui.componentes.SearchInputField
import com.aiko.domain.model.LineModel
import com.alabia.manager.ui.state.TaskState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.androidx.compose.getViewModel

@Composable
fun ScreenBusLines(
    navController: NavController,
    viewModel: ViewModelBusLine = getViewModel()
) {
    val state by viewModel.state.collectAsState()
    val taskState by viewModel.taskState.collectAsState()

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = Color.Transparent, darkIcons = true)
    systemUiController.setNavigationBarColor(MaterialTheme.colorScheme.surfaceVariant)

    Column(
        modifier = Modifier
            .navigationBarsPadding()
            .statusBarsPadding()
    ) {
        Scaffold(
            titleTopBar = "Linhas de ônibus".uppercase(),
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
            showBottomBarNavigation = true,
            bottomNavigationBar = { NavigationBar(navController) },
            contentToUse = { paddingValue ->
                Box(modifier = Modifier.padding(paddingValue)) {
                    Column {
                        Row(modifier = Modifier.padding(16.dp)) {
                            SearchInputField(
                                value = state.inputSearch ?: "",
                                placeholderText = "Digite o nome da linha",
                                onValueChange = { value ->
                                    viewModel.onEvent(
                                        BusLineEvent.InputChange(
                                            value
                                        )
                                    )
                                },
                                onSearchClick = { viewModel.onEvent(BusLineEvent.Submit) },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        Row {
                            if (taskState == TaskState.Loading) {
                                IndeterminateCircularIndicator()
                            } else {
                                state.busLineList?.let { LinhaListComposable(it) }
                            }
                        }
                    }
                }
            }
        )
    }
}


@Composable
fun LinhaListComposable(lineModels: List<LineModel?>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(lineModels.size) { index ->
            val linha = lineModels[index]
            linha?.let {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(Color.LightGray.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                        .padding(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Linha: ${linha.letreiroNumerico ?: "Desconhecida"}",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            text = "Sentido: ${linha.sentido ?: "Não disponível"}",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = "Tipo: ${linha.tipoLinha ?: "Não disponível"}",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = "Terminal Principal: ${linha.terminalPrincipal ?: "Não disponível"}",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = "Terminal Secundário: ${linha.terminalSecundario ?: "Não disponível"}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}
