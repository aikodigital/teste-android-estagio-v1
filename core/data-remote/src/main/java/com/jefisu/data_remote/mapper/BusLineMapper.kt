package com.jefisu.data_remote.mapper

import com.jefisu.data_remote.dto.BusLineDto
import com.jefisu.domain.model.BusLine

fun BusLineDto.toLine(): BusLine {
    val lineModeIndicator = when (lineModeIndicatorCode) {
        in listOf(21, 23, 32, 41) -> BusLine.LineModeIndicator.SERVICE
        else -> BusLine.LineModeIndicator.BASE // 10
    }

    return BusLine(
        idRoute = idRoute,
        isCircularMode = isCircularMode,
        line = line,
        direction = direction,
        lineModeIndicator = lineModeIndicator,
        departureTerminal = departureTerminal,
        arrivalTerminal = arrivalTerminal
    )
}