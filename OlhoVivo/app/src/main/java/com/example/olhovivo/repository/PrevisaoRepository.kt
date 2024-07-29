package com.example.olhovivo.repository

import com.example.olhovivo.api.OlhoVivoAPI
import com.example.olhovivo.model.PrevisaoResponse

class PrevisaoRepository(private val api: OlhoVivoAPI) {
    suspend fun getPrevisao(codigoParada: Int, codigoLinha: Int): PrevisaoResponse {
        return api.obterPrevisaoChegada(codigoParada, codigoLinha).execute().body() ?: throw Exception("Error fetching forecast")
    }
}
