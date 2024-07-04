package com.example.transportesp.ui

data class ResponseWrapper(
    val hr: String,
    val p: PredictionResponse
)

data class PredictionResponse(
    val cp: Int,
    val np: String,
    val py: Double,
    val px: Double,
    val l: List<LinePrediction>
)

data class LinePrediction(
    val c: String,
    val cl: Int,
    val sl: Int,
    val lt0: String,
    val lt1: String?,
    val qv: Int,
    val vs: List<VehiclePrediction>
)

data class VehiclePrediction(
    val p: String,
    val t: String,
    val a: Boolean,
    val ta: String,
    val py: Double,
    val px: Double,
    val sv: Any?,
    val `is`: Any?
)


data class ArrivalPrediction(
    val lt0: String,
    val p: String,
    val t: String
)

