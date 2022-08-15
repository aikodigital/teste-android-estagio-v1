package com.martini.spnoponto.presentation.components.lineDetails.content.maps

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.martini.spnoponto.R
import com.martini.spnoponto.domain.entities.linePosition.LinePosition

@Composable
fun LinePositionGoogleMapMarkerLoaded(
    positions: List<LinePosition>
) {
    positions.forEach { position ->
        val local = LatLng(position.latitude, position.longitude)
        val snippet = if (position.disabledFriendly) {
            stringResource(R.string.disabled_friendly)
        } else {
            stringResource(R.string.not_disabled_friendly)
        }
        Marker(
            state = MarkerState(local),
            title = position.prefix,
            snippet = snippet
        )
    }
}