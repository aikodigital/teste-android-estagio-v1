package br.vino.transmobisp.model

data class LineForecast(
    val cl: Int, // Line code
    val lt0: String, // Sign line - direction 1
    val lt1: String, // Sign line - direction 2
    val qv: Int, // Vehicle amount
    val vs: List<VehicleForecast> // List of vehicle predictions
)
