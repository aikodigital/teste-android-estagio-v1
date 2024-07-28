package com.example.aikodigital.service

import com.example.aikodigital.service.Response.posicao.PosicaoResponseList
import retrofit2.Call
import retrofit2.http.GET

interface PosicaoService {
    @GET("Posicao")
    fun getPosicao():Call<PosicaoResponseList>
}