package com.example.aikodigital.service.Response.previsao_chegada

data class PrevisaoChegadaResponse(
    val cp: Int,
    val np: String,
    val py: Double,
    val px: Double,
    val vs: List<PrevisaoChegadaVeiculoResponse>
)
