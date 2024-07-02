package br.com.samuel.testeaiko.core.domain.model

import java.time.LocalTime

data class BusStopForecast(
    val lineCode: Int,
    val stopCode: Int,
    val prefix: Int,
    val isAccessible: Boolean,
    val timestamp: LocalTime,
    val latitude: Double,
    val longitude: Double
)
