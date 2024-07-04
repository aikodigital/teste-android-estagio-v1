package br.vino.transmobisp.model

import java.io.Serializable

data class Vehicle(
    val p: String, // Vehicle prefix
    val a: Boolean, // Accessible vehicle
    val ta: String, // Date/Time position
    val py: Double, // Latitude
    val px: Double // Longitude
) : Serializable
