package com.example.app.data.api

import com.example.app.data.model.AllLinesResponse
import com.example.app.data.model.LineResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceAPI {
    @GET("Posicao")
    suspend fun getLines(): AllLinesResponse

    @GET("Posicao/Linha/")
    suspend fun getLineByCode(
        @Query("codigoLinha") code: String
    ): List<LineResponse>
}
