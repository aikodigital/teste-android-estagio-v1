package com.example.olhovivo.repository

import com.example.olhovivo.api.OlhoVivoAPI
import java.io.File

class VelocidadeRepository(private val api: OlhoVivoAPI) {
    suspend fun obterMapaVelocidade(sentido: String? = null): File {
        return api.obterMapaVelocidade(sentido).execute().body() ?: throw Exception("Error fetching speed map")
    }
}
