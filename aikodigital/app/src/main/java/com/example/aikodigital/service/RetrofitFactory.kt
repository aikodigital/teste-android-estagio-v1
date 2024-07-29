package com.example.aikodigital.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {
    private val URL_BASE = "https://aiko-olhovivo-proxy.aikodigital.io/"
    private val retrofitFactory = Retrofit
        .Builder()
        .baseUrl(URL_BASE)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun linhasService(): LinhasService{
        return retrofitFactory.create(LinhasService::class.java)
    }

    fun veiculosService(): VeiculosService{
        return retrofitFactory.create(VeiculosService::class.java)
    }

    fun previsaoChegadaService(): PrevisaoChegadaService{
        return retrofitFactory.create(PrevisaoChegadaService::class.java)
    }

    fun corredoresService(): CorredoresService{
        return retrofitFactory.create((CorredoresService::class.java))
    }
}

class RetrofitFactoryMaps {
    private val URL_BASE = "https://maps.googleapis.com/"
    private val retrofitFactory = Retrofit
        .Builder()
        .baseUrl(URL_BASE)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun mapsService(): MapsApiService{
        return retrofitFactory.create((MapsApiService::class.java))
    }
}