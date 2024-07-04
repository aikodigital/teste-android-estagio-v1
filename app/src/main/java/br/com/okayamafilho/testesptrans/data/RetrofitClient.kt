package br.com.okayamafilho.testesptrans.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val spTransAPI by lazy {
        Retrofit.Builder()
            .baseUrl("http://api.olhovivo.sptrans.com.br/v2.1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder().build()
            )
            .build()
            .create(SPTransAPI::class.java)
    }
}