package com.tiagomaciel.olhovivo.api

import com.tiagomaciel.olhovivo.api.dataClass.StopLocation
import com.tiagomaciel.olhovivo.api.dataClass.VehicleLines
import com.tiagomaciel.olhovivo.api.dataClass.VehiclePosition
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("Login/Autenticar")
    fun authenticate(@Query("token") token: String): Call<Boolean>

    @GET("Posicao")
    fun getVehiclePositions(
    ): Call<VehiclePosition>

    @GET("Linha/Buscar")
    fun getBusLines(@Query("termosBusca") searchTerms: String): Call<List<VehicleLines>?>

    @GET("Parada/Buscar")
    fun getVehicleStops(@Query("termosBusca") searchTerms: String): Call<List<StopLocation>?>
}
