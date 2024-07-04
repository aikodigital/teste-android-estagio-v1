package br.vino.transmobisp.model.vehicles_lines_from_stop

import java.io.Serializable

data class StopWithLines(
    val cp: Int,
    val np: String,
    val py: Double,
    val px: Double,
    val l: List<LineWithVehicles>
): Serializable
