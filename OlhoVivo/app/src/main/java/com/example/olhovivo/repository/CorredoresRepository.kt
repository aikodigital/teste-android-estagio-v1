package com.example.olhovivo.repository

import com.example.olhovivo.api.OlhoVivoAPI
import com.example.olhovivo.model.Corredor
import com.seuprojeto.model.Corredor

class CorredoresRepository(private val api: OlhoVivoAPI) {
    suspend fun obterCorredores(): List<Corredor> {
        return api.obterCorredores().execute().body() ?: emptyList()
    }
}
