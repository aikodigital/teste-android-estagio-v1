package br.vino.transmobisp.model.vehicles_lines_from_stop

import br.vino.transmobisp.model.stops_from_line.VehicleStatus
import java.io.Serializable

data class LineWithVehicles(
    val c: String,
    val cl: Int,
    val sl: Int,
    val lt0: String,
    val lt1: String,
    val qv: Int,
    val vs: List<VehicleStatus>
): Serializable