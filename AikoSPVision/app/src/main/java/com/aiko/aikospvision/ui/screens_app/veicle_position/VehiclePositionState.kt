package com.aiko.aikospvision.ui.screens_app.veicle_position

data class VehiclePositionState(
    val vehicleId: Long? = null,
    val vehicleOrigin: String? = null,
    val vehicleDestination: String? = null,
    val vehicleCompleteSign: String? = null,
    val vehicleLineSense: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val isLoading: Boolean = false,
)