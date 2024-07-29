package com.example.olhovivo.repository

import com.example.olhovivo.api.OlhoVivoAPI
import com.example.olhovivo.model.ResultadoPesquisa

class FiltroRepository(private val api: OlhoVivoAPI) {
    suspend fun pesquisar(term: String, filtros: Map<String, Any>): List<ResultadoPesquisa> {
        // Implementar lógica de pesquisa e aplicação de filtros
        return api.pesquisar(term, filtros).execute().body() ?: emptyList()
    }
}
