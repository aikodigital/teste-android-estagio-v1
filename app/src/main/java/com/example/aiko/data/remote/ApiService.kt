package com.example.aiko.data.remote

import com.example.aiko.data.model.Position
import com.example.aiko.data.model.StopBusItem
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("Posicao")
    suspend fun getPosition(): Position

    @GET("Parada/Buscar?termosBusca=")
    suspend fun getStopBus(): List<StopBusItem>

    @POST("Login/Autenticar?token=402b9ee6bea7d98f703c99da5b4be40ddf458e016b23cd83598247223356083c")
    suspend fun postAuth(): Boolean
}