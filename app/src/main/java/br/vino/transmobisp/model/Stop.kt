package br.vino.transmobisp.model

import java.io.Serializable

data class Stop(
    val cp: Int, // Stop code
    val np: String, // Stop name
    val ed: String, // Address
    val py: Double, // Latitude
    val px: Double // Longitude
) : Serializable
