package com.example.aikodigital.service.Response.posicao

data class PosicaoResponse (
    val c: String,
    val cl: Int,
    val sl: Int,
    val lt0: String,
    val lt1: String,
    val qv: Int,
    val vs: List<VeiculosResponse>
)
