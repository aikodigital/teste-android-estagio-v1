package br.com.samuel.testeaiko.infra.network.services

import br.com.samuel.testeaiko.core.domain.dtos.CorridorResponse
import retrofit2.Response
import retrofit2.http.GET

interface CorridorsService {

    /**
     * A categoria Corredores possibilita uma consulta que retorna todos os corredores inteligentes da cidade de São Paulo.
     * Nesta categoria existem os seguintes métodos de consulta disponíveis:
     */
    @GET("/Corredor")
    suspend fun searchCorridors(): Response<List<CorridorResponse>>
}