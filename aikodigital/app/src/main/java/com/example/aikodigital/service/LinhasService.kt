package com.example.aikodigital.service

import com.example.aikodigital.service.responses.linhas.LinhasResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface LinhasService {
    @GET("Linha/Buscar")
    fun getLinhas(@Query("termosBusca") termoBusca: String):Call<List<LinhasResponse>>
}