package br.dev.saed.saedrastreamentosapi.models

data class LinhasPosicao(
    val c: String,
    val cl: Int,
    val lt0: String,
    val lt1: String,
    val qv: Int,
    val sl: Int,
    val vs: List<VeiculosPosicao>
)