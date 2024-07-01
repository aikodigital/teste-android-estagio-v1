package com.tiagomaciel.olhovivo.api

data class VehiclePosition(
    val hr: String,
    val l: List<Location>
)

data class Location(
    val c: String,
    val cl: Int,
    val sl: Int,
    val lt0: String,
    val lt1: String,
    val qv: Int,
    val vs: List<Visit>
)

data class Visit(
    val p: Int,
    val a: Boolean,
    val ta: String,
    val py: Double,
    val px: Double
)