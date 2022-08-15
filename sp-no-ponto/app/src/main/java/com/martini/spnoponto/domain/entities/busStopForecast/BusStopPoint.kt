package com.martini.spnoponto.domain.entities.busStopForecast

data class BusStopPoint(
    val code: Int,
    val lines: List<BusStopPointLines>,
    val name: String,
    val longitude: Double,
    val latitude: Double
)
