package com.example.olhovivo.repository

import com.example.olhovivo.api.OlhoVivoAPI
import com.example.olhovivo.model.Linha
import retrofit2.Call

class LinhaRepository(private val apiService: OlhoVivoAPI) {
    suspend fun getLinhas(termosBusca: String): List<Linha> {
        return apiService.buscarLinhas(termosBusca).execute().body() ?: emptyList()
    }
}
