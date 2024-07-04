package com.example.transportesp.http

import com.example.transportesp.data.BusStop
import com.example.transportesp.data.Line
import com.example.transportesp.ui.ResponseWrapper
import com.example.transportesp.data.VehiclePosition
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface OlhoVivoApi {

    @POST("Login/Autenticar")
    suspend fun autenticar(@Query("token") token: String): Boolean

    @GET("Posicao")
    suspend fun getPosicoesVeiculos(@Header("Authorization") token: String): VehiclePosition

    @GET("Parada/Buscar")
    suspend fun buscarParadas(@Header("Authorization") token: String,
                              @Query("termosBusca") termosBusca: String): List<BusStop>

    @GET("Linha/Buscar")
    suspend fun buscarLinhas(
        @Header("Authorization") token: String,
        @Query("termosBusca") termosBusca: String
    ): List<Line>

    @GET("Previsao/Parada")
    suspend fun buscarPrevisoesChegada(
        @Header("Authorization") token: String,
        @Query("codigoParada") codigoParada: Int
    ): ResponseWrapper





}
