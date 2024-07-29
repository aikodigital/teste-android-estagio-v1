package com.example.olhovivo.repository

import com.example.olhovivo.api.OlhoVivoAPI
import com.example.olhovivo.model.Parada
import com.example.olhovivo.network.ApiClient
import retrofit2.Call

class ParadaRepository(private val api: OlhoVivoAPI) {
    suspend fun buscarParadas(termo: String): List<Parada> {
        return api.buscarParadas(termo).execute().body() ?: emptyList()
    }

    suspend fun buscarParadasPorLinha(codigoLinha: Int): List<Parada> {
        return api.buscarParadasPorLinha(codigoLinha).execute().body() ?: emptyList()
    }
}
