package com.example.aikodigital.service

import com.example.aikodigital.service.responses.previsao_chegada.PrevisaoChegadaResponseList
import com.example.aikodigital.service.responses.previsao_chegada.parada.PrevisaoChegadaParadaResponseList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PrevisaoChegadaService {
    @GET("Previsao/Linha")
    fun getPrevisaoChegada(@Query("codigoLinha") codigoLinha: Int):Call<PrevisaoChegadaResponseList>

    @GET("Previsao/Parada")
    fun getPrevisaoChegadaParada(@Query("codigoParada") codigoParada: Int):Call<PrevisaoChegadaParadaResponseList>
}