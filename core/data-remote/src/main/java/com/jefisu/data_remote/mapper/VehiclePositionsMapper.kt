package com.jefisu.data_remote.mapper

import com.jefisu.data_remote.dto.VehiclePositionsDto
import com.jefisu.domain.model.VehiclePositions

fun VehiclePositionsDto.toVehiclePositions(): VehiclePositions {
    return VehiclePositions(
        lastUpdate = lastUpdate,
        busLines = lines.map { it.toLine() }
    )
}