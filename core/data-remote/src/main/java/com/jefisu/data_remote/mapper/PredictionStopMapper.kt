package com.jefisu.data_remote.mapper

import com.jefisu.data_remote.dto.PredictionStopDetailsDto
import com.jefisu.data_remote.dto.PredictionStopDto
import com.jefisu.domain.model.PredictionStop
import com.jefisu.domain.model.PredictionStopDetails

fun PredictionStopDto.toPredictionStop(): PredictionStop {
    return PredictionStop(
        lastUpdate = lastUpdate,
        predictionStop = stopDetails.toPredictionStopDetails()
    )
}

fun PredictionStopDetailsDto.toPredictionStopDetails(): PredictionStopDetails {
    return PredictionStopDetails(
        stopCode = stopCode,
        stopName = stopName,
        latitude = latitude,
        longitude = longitude,
        lines = lines.map { it.toLine() }
    )
}