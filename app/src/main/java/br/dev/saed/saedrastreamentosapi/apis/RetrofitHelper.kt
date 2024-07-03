package br.dev.saed.saedrastreamentosapi.apis

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper {

    companion object {
        private const val BASE_URL = "https://aiko-olhovivo-proxy.aikodigital.io"
        const val TOKEN = "?token=22f41935fe986cdcd19f83d1330211552b9853a42829d17029ca1bae58b1ad3d"

        val retrofit: Retrofit = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
}