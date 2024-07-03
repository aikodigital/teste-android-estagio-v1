package br.dev.saed.saedrastreamentosapi.models

data class ParadaPrevisao(
    val cp: Int,
    val l: List<LinhasPrevisao>,
    val np: String,
    val px: Double,
    val py: Double
)