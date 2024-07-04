package com.jefisu.data_remote.mapper

import com.jefisu.data_remote.dto.BusStopDto
import com.jefisu.domain.model.BusStop

fun BusStopDto.toBusStop(): BusStop {
    return BusStop(
        stopCode = stopCode,
        stopName = stopName,
        address = address,
        latitude = latitude,
        longitude = longitude
    )
}