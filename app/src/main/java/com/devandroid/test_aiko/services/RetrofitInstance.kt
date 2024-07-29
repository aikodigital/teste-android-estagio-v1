package com.devandroid.test_aiko.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {

    private const val URL_LIVE_EYE = " https://aiko-olhovivo-proxy.aikodigital.io/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(URL_LIVE_EYE)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    val api : ApiService by lazy {
        retrofit.create(ApiService ::class.java)
    }
}