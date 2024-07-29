package com.aiko.bus.ui.theme

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MapUiSettings

val MAP_UI_SETTINGS = MapUiSettings(
    zoomControlsEnabled = false,
    myLocationButtonEnabled = false,
    mapToolbarEnabled = false,
    compassEnabled = false,
    indoorLevelPickerEnabled = false,
)

val SAO_PAULO = LatLng(-23.550520, -46.633309)

const val UPDATE_IN = 30 * 1000L