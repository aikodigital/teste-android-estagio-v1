package com.andreesperanca.deolhonobus.data.remote

import com.andreesperanca.deolhonobus.util.BASE_URL
import com.andreesperanca.deolhonobus.util.PROXY_URL_AIKO
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppRetrofit {
    
    private val client by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(PROXY_URL_AIKO)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val webService: RetrofitService by lazy {
        retrofit.create(RetrofitService::class.java)
    }
}