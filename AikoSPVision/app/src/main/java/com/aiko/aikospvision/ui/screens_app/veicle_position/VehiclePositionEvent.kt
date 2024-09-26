package com.aiko.aikospvision.ui.screens_app.veicle_position

sealed class VehiclePositionEvent {
    data class SaveVehicleInformationEvent(
        val longitude: Double? = null,
        val latitude: Double? = null ,
        val vehicleId: Long? = null,
        val vehicleOrigin: String? = null,
        val vehicleDestination: String? = null,
        val vehicleCompleteSign: String? = null,
        val vehicleLineSense: String? = null,
    ) : VehiclePositionEvent()
}