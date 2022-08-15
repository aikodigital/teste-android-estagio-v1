package com.martini.spnoponto.presentation.components.lineDetails.content.maps

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.spnoponto.domain.usecases.GetLinePositionState

@Composable
fun LinePositionGoogleMapMarker(
    getLinePositionViewModel: GetLinePositionViewModel = hiltViewModel()
) {
    when(val state = getLinePositionViewModel.state.value) {
        is GetLinePositionState.Loaded -> {
            LinePositionGoogleMapMarkerLoaded(state.positions)
        }
        else -> {}
    }
}