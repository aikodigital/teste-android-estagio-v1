package br.com.aiko.estagio.bussp.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class PrevisaoChegadaLinha(
    val hr: String,
    val ps: List<PontoParadaLinha>
)

@Serializable
data class PontoParadaLinha(
    val cp: Int,
    val np: String,
    val py: Double,
    val px: Double,
    val vs: List<VeiculoLocalizado>
)

