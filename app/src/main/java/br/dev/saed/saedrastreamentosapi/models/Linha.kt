package br.dev.saed.saedrastreamentosapi.models

data class Linha(
    val cl: Int,
    val lc: Boolean,
    val lt: String,
    val sl: Int,
    val tl: Int,
    val tp: String,
    val ts: String
)