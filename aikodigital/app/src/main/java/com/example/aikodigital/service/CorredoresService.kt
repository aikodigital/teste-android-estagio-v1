package com.example.aikodigital.service

import com.example.aikodigital.service.Response.corredores.CorredoresParadasResponse
import com.example.aikodigital.service.Response.corredores.CorredoresResponse
import com.example.aikodigital.service.Response.posicao.PosicaoResponseList
import com.example.aikodigital.service.Response.veiculos.VeiculosResponseList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CorredoresService {
    @GET("Corredor")
    fun getCorredores():Call<List<CorredoresResponse>>

    @GET("Parada/BuscarParadasPorCorredor")
    fun getCorredoresParadas(@Query("codigoCorredor") codigoCorredor : Int):Call<List<CorredoresParadasResponse>>
}