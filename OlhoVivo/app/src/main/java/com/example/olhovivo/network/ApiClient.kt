package com.example.olhovivo.network

import com.example.olhovivo.api.OlhoVivoAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "http://api.olhovivo.sptrans.com.br/v2.1/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: OlhoVivoAPI by lazy {
        retrofit.create(OlhoVivoAPI::class.java)
    }
}
