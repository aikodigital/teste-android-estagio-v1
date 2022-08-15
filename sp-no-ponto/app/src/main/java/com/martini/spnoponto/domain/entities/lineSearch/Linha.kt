package com.martini.spnoponto.domain.entities.lineSearch

import kotlinx.serialization.Serializable

//Essa classe precisa de serialização
//porque é passada como argumento entre
//screens e Jetpack Compose só permite
//Tipos simples como Int, String etc
//Por isso, converte-se a classe em
//String com @Serializable
@Serializable
data class Linha (
    val code: Int,
    val circular: Boolean,
    val firstSign: String,
    val way: Int,
    val secondSign: Int,
    val descriptionPrimary: String,
    val descriptionSecondary: String
)
