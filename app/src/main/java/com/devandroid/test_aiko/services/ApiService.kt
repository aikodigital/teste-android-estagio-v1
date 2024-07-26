package com.devandroid.test_aiko.services

import com.devandroid.test_aiko.models.Line
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("Login/Autenticar")
    suspend fun authenticate(@Query("token") token: String): Response<Boolean>

    @GET("Linha/Buscar")
    suspend fun searchLine(@Query("termosBusca") termosBusca: String): Response<List<Line>>

}
