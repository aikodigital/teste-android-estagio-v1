package com.andreesperanca.deolhonobus.models

import com.google.android.gms.maps.model.LatLng

data class Relations(
    val vehiclePrefix: String,
    val accessibleForDisability: Boolean,
    val timeLocalized: String,
    val py: Double,
    val px: Double,
    val latLng: LatLng = LatLng(py, px)
)
