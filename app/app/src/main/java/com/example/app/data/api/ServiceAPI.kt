package com.example.app.data.api

import com.example.app.data.model.AllLinesResponse
import com.example.app.data.model.LineResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ServiceAPI {
    @GET("Posicao")
    suspend fun getLines(): AllLinesResponse

    @GET("Posicao/Linha")
    suspend fun getLineByCode(
        @Query("codigoLinha") code: String
    ): LineResponse

    @POST("Login/Autenticar")
    suspend fun authenticate(
        @Query("token") token: String
    ): Response<Boolean>
}
