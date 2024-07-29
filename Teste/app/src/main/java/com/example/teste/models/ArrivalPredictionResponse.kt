package com.example.teste.models

data class ArrivalPredictionResponse(
    val hr: String,
    val p: Stop,
    val l: List<LineArrival>
)

data class LineArrival(
    val c: String,
    val cl: Int,
    val sl: Int,
    val lt0: String,
    val lt1: String,
    val qv: Int,
    val vs: List<VehicleArrival>
)

data class VehicleArrival(
    val p: String,
    val t: String,
    val a: Boolean,
    val ta: String,
    val py: Double,
    val px: Double
)
