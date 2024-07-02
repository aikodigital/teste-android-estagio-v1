package br.com.samuel.testeaiko.core.domain.model

import java.time.ZonedDateTime

data class BusPosition(
    val prefix: Int,
    val isAccessible: Boolean,
    val timestamp: ZonedDateTime,
    val latitude: Double,
    val longitude: Double
)
