package com.hilguener.spbusao.data.http

import com.hilguener.spbusao.domain.model.Lines
import com.hilguener.spbusao.domain.model.Parades
import com.hilguener.spbusao.domain.model.PosVehicles
import com.hilguener.spbusao.domain.model.PosVehiclesByLines
import com.hilguener.spbusao.domain.model.PrevArrival
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface OlhoVivoApi {
    @GET("Posicao")
    suspend fun getPosVehicles(
        @Header("Cookie") certificate: String,
    ): Response<PosVehicles>

    @GET("Posicao/Linha")
    suspend fun getPosVehiclesByLine(
        @Header("Cookie") certificate: String,
        @Query("codigoLinha") idLine: Int,
    ): Response<PosVehiclesByLines>

    @GET("Linha/Buscar")
    suspend fun getLines(
        @Header("Cookie") certificate: String,
        @Query("termosBusca") line: String,
    ): Response<List<Lines>>

    @GET("Parada/Buscar")
    suspend fun getParades(
        @Header("Cookie") certificate: String,
        @Query("termosBusca") term: String,
    ): Response<List<Parades>>

    @GET("Parada/BuscarParadasPorLinha")
    suspend fun getParadesByLine(
        @Header("Cookie") certificate: String,
        @Query("codigoLinha") idLine: Int,
    ): Response<List<Parades>>

    @GET("Previsao/Parada")
    suspend fun getPrevArrival(
        @Header("Cookie") certificate: String,
        @Query("codigoParada") coding: Int,
    ): Response<PrevArrival>

    @POST("Login/Autenticar")
    suspend fun authenticate(
        @Query("token") token: String,
    ): Response<Boolean>
}
