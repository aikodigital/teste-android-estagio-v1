package br.com.samuel.testeaiko.infra.network.services

import br.com.samuel.testeaiko.core.domain.dtos.ArrivalForecastStopResponse
import br.com.samuel.testeaiko.core.domain.dtos.StopForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArrivalForecastService {

    /**
     * Retorna uma lista com a previsão de chegada dos veículos de cada uma das linhas que atendem ao ponto de parada informado.
     *
     * @param stopCode - Código identificador da parada.
     * Este é um código identificador único de cada ponto de parada do sistema (por sentido) e pode ser obtido através do método BUSCAR da categoria Paradas
     */
    @GET("/Previsao/Parada")
    suspend fun getForecastByStop(@Query("codigoParada") stopCode: Int): Response<ArrivalForecastStopResponse>

}