package com.example.aikodigital.service

import com.example.aikodigital.service.responses.veiculos.VeiculosResponseList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface VeiculosService {
    @GET("Posicao/Linha")
    fun getPosicaoVeiculos(@Query("codigoLinha") codigoLinha: Int):Call<VeiculosResponseList>
}