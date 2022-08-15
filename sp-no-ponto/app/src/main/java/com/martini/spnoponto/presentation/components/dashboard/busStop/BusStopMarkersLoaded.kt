package com.martini.spnoponto.presentation.components.dashboard.busStop

import androidx.compose.runtime.Composable
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.martini.spnoponto.domain.entities.busStop.BusStop

@Composable
fun BusStopMarkersLoaded(
    stops: List<BusStop>,
    onClick: (Marker) -> Boolean
) {
    stops.forEach { busStop ->
        val local = LatLng(busStop.latitude, busStop.longitude)
        Marker(
            state = MarkerState(local),
            title = busStop.name,
            snippet = busStop.address,
            onClick = onClick,
            tag = busStop
        )
    }
}