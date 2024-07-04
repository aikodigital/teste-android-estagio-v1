package br.vino.transmobisp.model

data class Line(
    val cl: Int, // Identification line code
    val lc: Boolean, // Circular line
    val lt: String, // Sign line
    val sl: Int, // Line direction
    val tl: Int, // Line type
    val tp: String, // Main terminal
    val ts: String // Secundary terminal
)
