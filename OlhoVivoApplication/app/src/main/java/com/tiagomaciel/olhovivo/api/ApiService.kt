package com.tiagomaciel.olhovivo.api

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
}
