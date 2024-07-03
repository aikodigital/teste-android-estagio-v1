package br.com.aiko.estagio.bussp.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class Corredor(
    val cc: Int,
    val nc: String
)
