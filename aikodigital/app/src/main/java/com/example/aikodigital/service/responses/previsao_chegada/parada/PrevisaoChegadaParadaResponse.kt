package com.example.aikodigital.service.responses.previsao_chegada.parada

data class PrevisaoChegadaParadaResponse(
    val cp: Int,
    val np: String,
    val py: Double,
    val px: Double,
    val l: List<PrevisaoChegadaParadaLinhaResponse>
)
