package br.com.okayamafilho.testesptrans.data

import br.com.okayamafilho.testesptrans.domain.LineBus
import br.com.okayamafilho.testesptrans.domain.PositionBus
import br.com.okayamafilho.testesptrans.domain.SearchStopsByLine
import br.com.okayamafilho.testesptrans.domain.StopBus
import br.com.okayamafilho.testesptrans.domain.StopForecastLineBus
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface SPTransAPI {

    @POST("Login/Autenticar")
    suspend fun authResponse(
        @Query("token") token : String,
    ) : Response<Boolean>

    //Buscar Linhas
    @GET("Linha/Buscar")
    suspend fun searchLineBus(
        @Header("Cookie")
        cookie: String,
        @Query("termosBusca")
        termosBusca: String
    ): List<LineBus>

    //Buscar Linhas de parada
    @GET("Parada/Buscar")
    suspend fun searchStopBus(
        @Header("Cookie")
        cookie: String,
        @Query("termosBusca")
        termosBusca: String
    ): List<StopBus>

    //Buscar Posicao do veículo
    @GET("Posicao/Linha")
    suspend fun searchPositionBus(
        @Header("Cookie")
        cookie: String,
        @Query("codigoLinha")
        codigoLinha: String
    ): PositionBus

    //Buscar Linhas por ponto de parada
    @GET("Parada/BuscarParadasPorLinha")
    suspend fun searchStopBusLine(
        @Header("Cookie")
        cookie: String,
        @Query("codigoLinha")
        codigoLinha: Int
    ): List<SearchStopsByLine>

    //Retorna uma lista com a previsão de chegada dos veículos de
    // cada uma das linhas que atendem ao ponto de parada informado.
    @GET("Previsao/Parada")
    suspend fun searchStopForecastLineBus(
        @Header("Cookie")
        cookie: String,
        @Query("codigoParada")
        codigoParada: Int
    ): StopForecastLineBus
}