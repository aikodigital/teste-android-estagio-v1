package br.com.aiko.estagio.bussp.data.remote.response

import kotlinx.serialization.Serializable


@Serializable
data class PrevisaoChegada(
    val hr: String,
    val p: PontoParada
)

@Serializable
data class PontoParada(
    val cp: Int,
    val np: String,
    val py: Double,
    val px: Double,
    val l: List<LinhasLocalizada>
)

@Serializable
data class LinhasLocalizada(
    val c: String,
    val cl: Int,
    val sl: Int,
    val lt0: String,
    val lt1: String,
    val qv: Int,
    val vs: List<VeiculoLocalizado>
)

@Serializable
data class VeiculoLocalizado(
    val p: Int,
    val t: String,
    val a: Boolean,
    val ta: String,
    val py: Double,
    val px: Double
)
