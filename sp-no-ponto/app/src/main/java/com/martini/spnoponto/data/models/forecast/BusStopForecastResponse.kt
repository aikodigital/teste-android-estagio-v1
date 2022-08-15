package com.martini.spnoponto.data.models.forecast

import com.martini.spnoponto.common.NoBusPointInfoException
import com.martini.spnoponto.domain.entities.busStopForecast.BusStopForecast

//Classe gerada com o plugin "Kotlin data class file from JSON"
data class BusStopForecastResponse(
    val hr: String,
    val p: P?
)

fun BusStopForecastResponse.toBusStopForecast(): BusStopForecast {
    if (p == null) throw NoBusPointInfoException()
    return BusStopForecast(
        hr,
        p.toBusStopPoint()
    )
}