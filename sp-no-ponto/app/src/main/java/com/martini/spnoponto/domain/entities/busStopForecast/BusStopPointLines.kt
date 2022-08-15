package com.martini.spnoponto.domain.entities.busStopForecast

data class BusStopPointLines(
    val sign: String,
    val code: Int,
    val destinationSign: String,
    val originSign: String,
    val amountOfBusses: Int,
    val way: Int,
    val busses: List<BusStopPointBus>
)
