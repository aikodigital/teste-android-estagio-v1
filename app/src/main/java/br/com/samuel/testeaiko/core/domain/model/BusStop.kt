package br.com.samuel.testeaiko.core.domain.model

data class BusStop(
    val code: Int,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    var forecasts: List<BusStopForecast>? = null
)
