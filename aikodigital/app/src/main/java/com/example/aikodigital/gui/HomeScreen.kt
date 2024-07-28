package com.example.aikodigital.gui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aikodigital.R
import com.google.android.gms.location.LocationServices

@Composable
fun HomeScreen(navController: NavController){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.welcome),
                fontSize = 45.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.fonts)
            )

        }

        Button(
            onClick = {navController.navigate("menu")},
            modifier = Modifier.size(300.dp, 50.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.secondary)),
            border = BorderStroke(2.dp, colorResource(id = R.color.fonts))
        ) {
            Text(
                text = stringResource(id = R.string.enter),
                color = colorResource(id = R.color.fonts),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

    }
}



