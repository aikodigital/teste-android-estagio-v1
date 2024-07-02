package br.com.samuel.testeaiko.infra.network.services

import br.com.samuel.testeaiko.core.domain.dtos.StopResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StopsService {

    /**
     * Realiza uma busca por todos os pontos de parada atendidos por uma determinada linha.
     *
     * @param lineCode - Código identificador da linha.
     * Este é um código identificador único de cada linha do sistema (por sentido) e pode ser obtido através do método BUSCAR da categoria Linhas
     */
    @GET("/Parada/BuscarParadasPorLinha")
    suspend fun searchByLine(@Query("codigoLinha") lineCode: Int): Response<List<StopResponse>>

    /**
     * Retorna a lista detalhada de todas as paradas que compõem um determinado corredor.
     *
     * @param corridorCode - Código identificador do corredor. Este é um código identificador único de cada corredor do sistema e pode ser obtido através do método GET da categoria Corredores
     */
    @GET("/Parada/BuscarParadasPorCorredor")
    suspend fun searchByCorridor(@Query("codigoCorredor") corridorCode: Int): Response<List<StopResponse>>

}