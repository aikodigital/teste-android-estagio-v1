package com.jefisu.domain.model

data class Line(
    val code: String,
    val id: Int,
    val terminalDirection: TerminalDirection,
    val origin: String,
    val destination: String,
    val vehicleCount: Int,
    val vehicles: List<Vehicle>
) {
    enum class TerminalDirection {
        MAIN_TO_SECONDARY,
        SECONDARY_TO_MAIN
    }
}