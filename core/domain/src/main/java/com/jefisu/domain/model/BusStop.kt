package com.jefisu.domain.model

data class BusStop(
    val stopCode: Int,
    val stopName: String,
    val address: String,
    val latitude: Double,
    val longitude: Double
)
