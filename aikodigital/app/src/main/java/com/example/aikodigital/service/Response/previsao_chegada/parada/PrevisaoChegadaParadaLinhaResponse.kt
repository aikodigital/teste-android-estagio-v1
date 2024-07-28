package com.example.aikodigital.service.Response.previsao_chegada.parada

import com.example.aikodigital.service.Response.previsao_chegada.PrevisaoChegadaVeiculoResponse

data class PrevisaoChegadaParadaLinhaResponse(
    val c: String,
    val cl: Int,
    val sl: Int,
    val lt0: String,
    val lt1: String,
    val qv: Int,
    val vs: List<PrevisaoChegadaVeiculoResponse>
)
