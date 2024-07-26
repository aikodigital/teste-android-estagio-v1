package com.devandroid.test_aiko.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val URL_LIVE_EYE = "http://api.olhovivo.sptrans.com.br/v2.1/"

    val api : ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(URL_LIVE_EYE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}