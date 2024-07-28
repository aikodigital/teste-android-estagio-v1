package com.example.aikodigital.gui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.aikodigital.R
import com.example.aikodigital.model.MyViewModel

@Composable
fun LinesScreen(navController: NavController, viewModel: MyViewModel){
    var find by remember { mutableStateOf("") }
    val linhas by viewModel.linhas.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background))
            .padding(vertical = 20.dp, horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = find,
                onValueChange = {it ->
                    find = it
                },
                shape = RoundedCornerShape(20.dp),

                label = {
                    Text(text = stringResource(id = R.string.search))
                },
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = colorResource(id = R.color.fonts),
                    unfocusedLabelColor = colorResource(id = R.color.fonts),
                    focusedContainerColor = colorResource(id = R.color.secondary),
                    unfocusedContainerColor = colorResource(id = R.color.secondary),
                    focusedTextColor = colorResource(id = R.color.fonts),
                    unfocusedTextColor = colorResource(id = R.color.fonts),

                )
            )
            Spacer(modifier = Modifier.width(10.dp))
            Card(
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        viewModel.fetchLinhas(find)
                    },
                shape = RoundedCornerShape(25.dp),
                border = BorderStroke(2.dp, colorResource(id = R.color.fonts))
            ){
                Column(
                    modifier = Modifier
                        .background(colorResource(id = R.color.secondary))
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.search),
                        contentDescription ="",
                        tint = colorResource(id = R.color.fonts)
                    )
                }

            }
        }

        LazyColumn {
            items(linhas){it ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(top = 10.dp)
                        .clickable {
                            viewModel.fetchParadas(it.cl)
                            viewModel.fetchVeiculos(it.cl)
                            viewModel.fetchPrevisaoChegada(it.cl)
                            navController.navigate("busStop")
                        },
                    shape = RoundedCornerShape(30.dp),
                    border = BorderStroke(2.dp, colorResource(id = R.color.fonts))
                ){
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(colorResource(id = R.color.secondary)),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = R.string.line) + " ${it.lt} - ${it.tl}",
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.fonts)
                        )
                        Text(
                            text = stringResource(id = R.string.terminal) + " ${ if (it.sl == 1) it.tp else it.ts }",
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.fonts)
                        )
                    }
                }
            }
        }

    }
}