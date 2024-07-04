package com.jefisu.domain.model

data class VehiclePositions(
    val lastUpdate: String,
    val busLines: List<Line>
)