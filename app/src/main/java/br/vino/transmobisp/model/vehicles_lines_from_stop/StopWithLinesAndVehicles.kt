package br.vino.transmobisp.model.vehicles_lines_from_stop

import java.io.Serializable

data class StopWithLinesAndVehicles(
    val hr: String,
    val p: StopWithLines
) : Serializable
