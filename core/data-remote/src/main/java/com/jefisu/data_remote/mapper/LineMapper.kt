package com.jefisu.data_remote.mapper

import com.jefisu.data_remote.dto.LineDto
import com.jefisu.domain.model.Line

fun LineDto.toLine(): Line {
    val terminalDirection = if (terminalDirectionCode == 1) {
        Line.TerminalDirection.MAIN_TO_SECONDARY
    } else {
        // terminalDirectionCode == 2
        Line.TerminalDirection.SECONDARY_TO_MAIN
    }
    return Line(
        code = code,
        id = lineCode,
        terminalDirection = terminalDirection,
        origin = origin,
        destination = destination,
        vehicleCount = vehicleCount,
        vehicles = vehicles.map { it.toVehicle() }
    )
}