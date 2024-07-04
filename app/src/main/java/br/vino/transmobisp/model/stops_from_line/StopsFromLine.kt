package br.vino.transmobisp.model.stops_from_line

import java.io.Serializable

data class StopsFromLine(
    val hr: String,
    val ps: List<StopWithVehicles>
): Serializable