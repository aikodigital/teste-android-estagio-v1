package com.jefisu.data_remote

import com.jefisu.data_remote.dto.BusLineDto
import com.jefisu.data_remote.dto.BusStopDto
import com.jefisu.data_remote.dto.PredictionStopDto
import com.jefisu.data_remote.dto.VehiclePositionsDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SpTransApi {

    @POST("Login/Autenticar?token=${BuildConfig.API_KEY}")
    suspend fun authenticate(): Boolean

    @GET("Posicao")
    suspend fun getVehiclePositions(): VehiclePositionsDto

    @GET("Linha/Buscar")
    suspend fun getBusLines(
        @Query("termosBusca") lineCodeOrName: String
    ): List<BusLineDto>

    @GET("Parada/Buscar")
    suspend fun getBusStops(
        @Query("termosBusca") lineCodeId: Int
    ): List<BusStopDto>

    @GET("Previsao/Parada")
    suspend fun getBusArrivalPredictionsByStopCode(
        @Query("codigoParada") stopCode: Int
    ): PredictionStopDto

    companion object {
        const val BASE_URL = "https://api.olhovivo.sptrans.com.br/v2.1/"
    }
}