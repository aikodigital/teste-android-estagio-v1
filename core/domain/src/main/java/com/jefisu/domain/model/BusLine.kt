package com.jefisu.domain.model

data class BusLine(
    val idRoute: Int,
    val isCircularMode: Boolean,
    val line: String,
    val direction: Int,
    val lineModeIndicator: LineModeIndicator,
    val departureTerminal: String,
    val arrivalTerminal: String
) {
    enum class LineModeIndicator {
        BASE,
        SERVICE
    }
}