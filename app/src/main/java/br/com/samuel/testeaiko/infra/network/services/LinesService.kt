package br.com.samuel.testeaiko.infra.network.services

import br.com.samuel.testeaiko.core.domain.dtos.LineResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LinesService {

    /**
     * Realiza uma busca das linhas do sistema com base no parâmetro informado.
     * Se a linha não é encontrada então é realizada uma busca fonetizada na denominação das linhas.
     */
    @GET("/Linha/Buscar")
    suspend fun search(@Query("termosBusca") query: String): Response<List<LineResponse>>

    /**
     * BuscarLinhaSentido
     * Realiza uma busca das linhas do sistema com base no parâmetro informado.
     * Se a linha não é encontrada então é realizada uma busca fonetizada na denominação das linhas.
     * A linha retornada será unicamente aquela cujo sentido de operação seja o informado no parâmetro sentido.
     */
    @GET("/Linha/BuscarLinhaSentido")
    suspend fun searchDirection(
        @Query("termosBusca") query: String,
        @Query("sentido") direction: Int
    ): Response<List<LineResponse>>

}