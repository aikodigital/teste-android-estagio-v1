package com.example.aiko_desafio.objetos

// Objeto para receber o JSON das paradas da classe Mapa_paradas
data class Paradas(
    var cp: Int = 0,
    var np: String = "",
    var ed: String = "",
    var py: Double = 0.0,
    var px: Double = 0.0,
)
