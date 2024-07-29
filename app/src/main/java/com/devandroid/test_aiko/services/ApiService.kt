package com.devandroid.test_aiko.services

import com.devandroid.test_aiko.models.ArrivalForecast
import com.devandroid.test_aiko.models.Line
import com.devandroid.test_aiko.models.PositionLine
import com.devandroid.test_aiko.models.PositionResponse
import com.devandroid.test_aiko.models.StopPoint
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("Login/Autenticar")
    suspend fun authenticate(@Query("token") token: String): Response<Boolean>

    @GET("Linha/Buscar")
    suspend fun getLines(@Query("termosBusca") termosBusca: String): Response<List<Line>>

    @GET("Parada/Buscar")
    suspend fun getStopPoints(@Query("termosBusca") termosBusca : String): Response<List<StopPoint>>

    @GET("Posicao")
    suspend fun getPositionVehicle(): Response<PositionResponse>

    @GET("Previsao/Parada")
    suspend fun getArrivalForecast(@Query("codigoParada") codigoParada: Int)
    : Response<ArrivalForecast>

    @GET("/Posicao/Linha")
    suspend fun getPositionWithLines(@Query("codigoLinha") codigoLinha : Int) : Response<PositionLine>

}
