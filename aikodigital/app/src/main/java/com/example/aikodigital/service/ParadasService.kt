package com.example.aikodigital.service

import com.example.aikodigital.service.Response.paradas.ParadasResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ParadasService {
    @GET("Parada/BuscarParadasPorLinha")
    fun getParadasPorLinha(@Query("codigoLinha") codigoLinha: Int): Call<List<ParadasResponse>>
}