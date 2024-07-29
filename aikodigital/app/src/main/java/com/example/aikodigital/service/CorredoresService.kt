package com.example.aikodigital.service

import com.example.aikodigital.service.responses.corredores.CorredoresParadasResponse
import com.example.aikodigital.service.responses.corredores.CorredoresResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CorredoresService {
    @GET("Corredor")
    fun getCorredores():Call<List<CorredoresResponse>>

    @GET("Parada/BuscarParadasPorCorredor")
    fun getCorredoresParadas(@Query("codigoCorredor") codigoCorredor : Int):Call<List<CorredoresParadasResponse>>
}