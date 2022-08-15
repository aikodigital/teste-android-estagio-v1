package com.martini.spnoponto.data.models.forecast

import com.martini.spnoponto.domain.entities.busStopForecast.BusStopPointBus

//Classe gerada com o plugin "Kotlin data class file from JSON"
data class V(
    val a: Boolean,
    val p: String,
    val px: Double,
    val py: Double,
    val t: String,
    val ta: String
)

fun V.toBusStopPointBus(): BusStopPointBus {
    return BusStopPointBus(
        a,
        p,
        px,
        py,
        t,
        ta
    )
}