package com.example.teste.models

data class VehiclePositionsResponse(
    val hr: String,
    val l: List<LinePosition>
)

data class LinePosition(
    val c: String,
    val cl: Int,
    val sl: Int,
    val lt0: String,
    val lt1: String,
    val qv: Int,
    val vs: List<VehiclePosition>
)

data class VehiclePosition(
    val p: Int, // Número do veículo
    val a: Boolean, // Acessibilidade
    val ta: String, // Horário de captura
    val py: Double, // Latitude
    val px: Double, // Longitude

)

