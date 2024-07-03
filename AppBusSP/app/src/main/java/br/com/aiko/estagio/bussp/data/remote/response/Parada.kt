package br.com.aiko.estagio.bussp.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class Parada(
    val cp: Int,
    val np: String,
    val ed: String,
    val py: Double,
    val px: Double
)
