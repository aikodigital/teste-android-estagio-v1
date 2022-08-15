package com.martini.spnoponto.data.models.forecast

import com.martini.spnoponto.domain.entities.busStopForecast.BusStopPoint

//Classe gerada com o plugin "Kotlin data class file from JSON"
data class P(
    val cp: Int,
    val l: List<L>,
    val np: String,
    val px: Double,
    val py: Double
)

fun P.toBusStopPoint(): BusStopPoint {
    return BusStopPoint(
        cp,
        l.map { it.toBusStopPointLines() },
        np,
        px,
        py
    )
}