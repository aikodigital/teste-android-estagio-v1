package com.jefisu.data_remote.mapper

import com.jefisu.data_remote.dto.VehicleDto
import com.jefisu.domain.model.Vehicle

fun VehicleDto.toVehicle(): Vehicle {
    return Vehicle(
        prefix = prefix,
        accessible = accessible,
        lastUpdate = lastUpdate,
        latitude = latitude,
        longitude = longitude,
        predictionTime = predictionTime
    )
}