package com.martini.spnoponto.domain.entities.busStopForecast

data class BusStopPointBus(
    val disabledFriendly: Boolean,
    val prefix: String,
    val longitude: Double,
    val latitude: Double,
    val forecast: String,
    val time: String
)
