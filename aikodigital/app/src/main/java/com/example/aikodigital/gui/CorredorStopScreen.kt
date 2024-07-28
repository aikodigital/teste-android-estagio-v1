package com.example.aikodigital.gui

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.maps.model.StyleSpan
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun CorredorStopScreen(navController: NavController, viewModel: MyViewModel, latitude: Double, longitude: Double){

    val location = LatLng(latitude,longitude)
    val paradas by viewModel.corredoresParada.collectAsState()
    var routePoly = mutableListOf<LatLng>()
    val route by viewModel.mapsRoute.collectAsState()

    LaunchedEffect(route) {
        if (route.routes.isNotEmpty()){
            routePoly = decodePoly(route.routes[0].overview_polyline.points)
        }
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 13f)
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

            Polyline(points = routePoly,
                color = colorResource(id = R.color.teal_700)
            )

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

            paradas.forEach{parada ->
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
            }
        }
    }
}

fun decodePoly(encoded: String): MutableList<LatLng> {
    val poly = ArrayList<LatLng>()
    var index = 0
    val len = encoded.length
    var lat = 0
    var lng = 0

    while (index < len) {
        var b: Int
        var shift = 0
        var result = 0
        do {
            b = encoded[index++].code - 63
            result = result or (b and 0x1f shl shift)
            shift += 5
        } while (b >= 0x20)
        val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
        lat += dlat

        shift = 0
        result = 0
        do {
            b = encoded[index++].code - 63
            result = result or (b and 0x1f shl shift)
            shift += 5
        } while (b >= 0x20)
        val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
        lng += dlng

        val p = LatLng(lat / 1E5, lng / 1E5)
        poly.add(p)
    }
    return poly
}
