package br.vino.transmobisp.model

data class StopForecast(
    val cp: Int, // Stop code
    val np: String, // StopName
    val l: List<LineForecast> //List of line arrival predictions
)