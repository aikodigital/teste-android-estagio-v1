package com.example.olhovivo.model

data class PosicoesResponse(
    val hr: String,
    val l: List<Linha>
)

data class Linha(
    val c: String,
    val cl: Int,
    val sl: Int,
    val lt0: String,
    val lt1: String,
    val qv: Int,
    val vs: List<Veiculo>,
    val lc: Boolean,
    val lt: String,
    val tl: Int,
    val tp: String,
    val ts: String
)
