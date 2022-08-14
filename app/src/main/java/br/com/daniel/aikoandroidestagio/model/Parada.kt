package br.com.daniel.aikoandroidestagio.model

import java.io.Serializable

data class Parada(
    val id: Int,
    val nome: String,
    val enderecoParada: String,
    val latitude: Double,
    val longitude: Double
) : Serializable

