package com.devandroid.test_aiko.models

data class Line(
    val CodigoLinha: Int,
    val DenominacaoTPTS: String,
    val DenominacaoTSTP: String,
    val Circular: Boolean,
    val Letreiro: String,
    val Sentido: Int,
    val Tipo: Int,
    val TipoTroncal: Int
)
