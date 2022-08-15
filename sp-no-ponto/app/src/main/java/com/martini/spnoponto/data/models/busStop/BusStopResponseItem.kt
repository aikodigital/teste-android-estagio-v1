package com.martini.spnoponto.data.models.busStop

import com.martini.spnoponto.domain.entities.busStop.BusStop

//Classe gerada com o plugin "Kotlin data class file from JSON"
data class BusStopResponseItem(
    val cp: Int,
    val ed: String,
    val np: String,
    val px: Double,
    val py: Double
)

fun BusStopResponseItem.toBusStop(): BusStop {
    return BusStop(
        cp,
        ed,
        np,
        px,
        py
    )
}