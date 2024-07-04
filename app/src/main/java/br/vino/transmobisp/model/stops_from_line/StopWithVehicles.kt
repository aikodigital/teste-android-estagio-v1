package br.vino.transmobisp.model.stops_from_line

import java.io.Serializable

data class StopWithVehicles(
    val cp: Int,
    val np: String,
    val py: Double,
    val px: Double,
    val vs: List<VehicleStatus>
): Serializable
