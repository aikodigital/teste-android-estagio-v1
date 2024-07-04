package com.jefisu.domain.model

data class PredictionStop(
    val lastUpdate: String,
    val predictionStop: PredictionStopDetails
)

data class PredictionStopDetails(
    val stopCode: Int,
    val stopName: String,
    val latitude: Double,
    val longitude: Double,
    val lines: List<Line>
)