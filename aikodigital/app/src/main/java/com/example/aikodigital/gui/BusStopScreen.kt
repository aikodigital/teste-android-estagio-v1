package com.example.aikodigital.gui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aikodigital.R
import com.example.aikodigital.model.MyViewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun BusStopScreen(navController: NavController, viewModel: MyViewModel, latitude: Double, longitude: Double){
    val location = LatLng(latitude,longitude)
    val paradas by viewModel.paradas.collectAsState()
    val veiculos by viewModel.veiculos.collectAsState()
    val previsao by viewModel.previsao.collectAsState()
    var routePoly = mutableListOf<LatLng>()
    val route by viewModel.mapsRoute.collectAsState()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 13f)
    }

    LaunchedEffect(route) {
        if (route.routes.isNotEmpty()){
            routePoly = decodePoly(route.routes[0].overview_polyline.points)
        }
    }

    if (paradas.isEmpty()){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.background))
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )    {
            Text(
                text = stringResource(id = R.string.busStop),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = colorResource(id = R.color.fonts)
                )
        }
    }else{
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ){
            MarkerInfoWindow(
                state = MarkerState(position = location),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .border(
                            BorderStroke(1.dp, Color.Black),
                            RoundedCornerShape(10)
                        )
                        .clip(RoundedCornerShape(10))
                        .background(Color.White)
                        .padding(20.dp)
                ) {
                }
            }

            previsao.ps.forEach{parada ->
                val position = LatLng(parada.py, parada.px)
                var poly = false


                MarkerInfoWindow(
                    state = MarkerState(position = position),
                    icon = BitmapDescriptorFactory.fromResource(R.drawable.location),
                    onClick = {
                        if (poly){
                            viewModel.fetchPrevisaoChegadaParada(parada.cp)
                            navController.navigate("busStopDetails")
                            poly = !poly
                        }
                        viewModel.fetchMapsRoute(
                            "$latitude, $longitude",
                            "${parada.py}, ${parada.px}"
                        )
                        poly = !poly
                        false
                    }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .border(
                                BorderStroke(1.dp, Color.Black),
                                RoundedCornerShape(10)
                            )
                            .clip(RoundedCornerShape(10))
                            .background(Color.White)
                            .padding(20.dp)
                    ) {
                        Text(text = stringResource(id = R.string.name) + ": " + parada.np)
                    }
                }

                parada.vs.forEach{veiculo ->
                    val position = LatLng(veiculo.py, veiculo.px)

                MarkerInfoWindow(
                    state = MarkerState(position = position),
                    icon = BitmapDescriptorFactory.fromResource(R.drawable.public_transport),

                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .border(
                                BorderStroke(1.dp, Color.Black),
                                RoundedCornerShape(10)
                            )
                            .clip(RoundedCornerShape(10))
                            .background(Color.White)
                            .padding(20.dp)
                            .width(200.dp)
                    ) {

                        Text(
                            text = stringResource(id = R.string.number) + ": " + veiculo.p,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.fonts)
                        )
                        Text(
                            text = stringResource(id = R.string.accessibility) + ": " +
                                    if (veiculo.a) stringResource(id = R.string.yes) else stringResource(id = R.string.no),
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.fonts)
                        )
                        Text(
                            text = stringResource(id = R.string.hrs) + ": " + veiculos.hr,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.fonts)
                        )
                    }
                }
            }
        }
        }
    }
}

