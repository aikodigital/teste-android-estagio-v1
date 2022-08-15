package com.martini.spnoponto.domain.entities.busStop

data class BusStop(
    val code: Int,
    val address: String,
    val name: String,
    val longitude: Double,
    val latitude: Double
)
