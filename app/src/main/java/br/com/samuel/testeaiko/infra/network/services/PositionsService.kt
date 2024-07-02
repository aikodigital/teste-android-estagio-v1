package br.com.samuel.testeaiko.infra.network.services

import br.com.samuel.testeaiko.core.domain.dtos.BusLinePositionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PositionsService {

    /**
     * Retorna uma lista com todos os veículos de uma determinada linha com suas devidas posições lat / long
     *
     * @param lineCode - Código identificador da linha.
     * Este é um código identificador único de cada linha do sistema (por sentido) e pode ser obtido através do método BUSCAR da categoria Linhas
     */
    @GET("/Posicao/Linha")
    suspend fun searchByLine(@Query("codigoLinha") lineCode: Int): Response<BusLinePositionResponse>

}