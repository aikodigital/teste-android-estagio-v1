package com.example.olhovivo.model

data class Parada(
    val cp: Int,
    val np: String,
    val py: Double,
    val px: Double,
    val l: List<Linha>
)