package com.example.aikodigital.service

import com.example.aikodigital.service.Response.posicao.PosicaoResponseList
import com.example.aikodigital.service.Response.previsao_chegada.PrevisaoChegadaResponseList
import com.example.aikodigital.service.Response.previsao_chegada.parada.PrevisaoChegadaParadaResponseList
import com.example.aikodigital.service.Response.veiculos.VeiculosResponseList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PrevisaoChegadaService {
    @GET("Previsao/Linha")
    fun getPrevisaoChegada(@Query("codigoLinha") codigoLinha: Int):Call<PrevisaoChegadaResponseList>

    @GET("Previsao/Parada")
    fun getPrevisaoChegadaParada(@Query("codigoParada") codigoParada: Int):Call<PrevisaoChegadaParadaResponseList>
}