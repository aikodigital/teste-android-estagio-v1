package com.example.aiko.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import com.example.aiko.R
import com.example.aiko.data.model.Position
import com.example.aiko.data.model.V
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapsApp(viewModel: PositionViewModel) {

    val positionMaps by viewModel.position.observeAsState(null)
    val stopBus by viewModel.stopBus.observeAsState(null)
    val auth by viewModel.auth.collectAsState(null)

    LaunchedEffect(Unit) {
        viewModel.fetchAuth()
        viewModel.fetchPosition()
        viewModel.fetchStopBus()
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(-23.85093875, -46.70703), 13f)
    }

    var px = positionMaps?.l?.flatMap { l -> l.vs.map { v -> Pair(v.px, l.lt0) } } //l.lt0 = ida
    var py = positionMaps?.l?.flatMap { l -> l.vs.map { v -> Pair(v.py, l.lt1) } } //l.lt1 = volta
    var avgPy = py?.toList()
    var avgPx = px?.toList()
    var stop = stopBus?.map { it }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
    ) {
        avgPx?.zip(avgPy ?: listOf())?.forEachIndexed {index, (px, py) ->
            Marker(
                state = MarkerState(position = LatLng(py.first, px.first)),
                title = "IDA: ${px.second}",
                snippet = "VOLTA: ${py.second}",
                icon = BitmapDescriptorFactory.fromResource(R.drawable.bus)
            )
        }
        stop?.forEach {
            Marker(
                state = MarkerState(position = LatLng(it.py, it.px)),
                title = it.np,
                snippet = it.ed,
                icon = BitmapDescriptorFactory.fromResource(R.drawable.stopbus)
            )
        }
    }
}
