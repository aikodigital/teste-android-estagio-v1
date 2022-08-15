package com.martini.spnoponto.data.models.forecast

import com.martini.spnoponto.domain.entities.busStopForecast.BusStopPointLines

//Classe gerada com o plugin "Kotlin data class file from JSON"
data class L(
    val c: String,
    val cl: Int,
    val lt0: String,
    val lt1: String,
    val qv: Int,
    val sl: Int,
    val vs: List<V>
)

fun L.toBusStopPointLines(): BusStopPointLines {
    return BusStopPointLines(
        c,
        cl,
        lt0,
        lt1,
        qv,
        sl,
        vs.map { it.toBusStopPointBus() }
    )
}