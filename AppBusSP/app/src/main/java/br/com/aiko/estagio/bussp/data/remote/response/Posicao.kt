package br.com.aiko.estagio.bussp.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Posicao(
    val hr: String,
    val l: List<Localizacao>
)

@Serializable
data class Localizacao(
    val c: String,
    val cl: Int,
    val sl: Int,
    val lt0: String,
    val lt1: String,
    val qv: Int,
    val vs: List<Veiculo>
)

@Serializable
data class Veiculo(
    val p: Int,
    val a: Boolean,
    val ta: String,
    val py: Double,
    val px: Double
)

@Serializable
data class PosVeiculo(
    val hr: String,
    val vs: List<Veiculo>
)