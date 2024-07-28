package com.example.aikodigital.gui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aikodigital.R
import com.example.aikodigital.model.MyViewModel

@Composable
fun CorredorsScreen(navController: NavController, viewModel: MyViewModel){

    val corredores by viewModel.corredores.collectAsState()

    if (corredores.isEmpty()){
        LaunchedEffect(Unit) {
            viewModel.fetchCorredores()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background))
            .padding(vertical = 20.dp, horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LazyColumn{
            items(corredores){ it ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(top = 10.dp)
                        .clickable {
                            viewModel.fetchCorredoresParadas(it.cc)
                            navController.navigate("corredorStop")
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
                            text = stringResource(id = R.string.line) + " ${it.cc}",
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.fonts)
                        )
                        Text(
                            text = it.nc,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.fonts)


                        )

                    }
                }
            }
        }

    }
}