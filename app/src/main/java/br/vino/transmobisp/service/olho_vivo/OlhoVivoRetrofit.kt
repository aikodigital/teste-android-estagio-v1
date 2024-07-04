package br.vino.transmobisp.service.olho_vivo

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.olhovivo.sptrans.com.br/v2.1/"

    private val client = OkHttpClient.Builder()
        .cookieJar(CookieManager())
        .build()

    val instance: OlhoVivoApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(OlhoVivoApiService::class.java)
    }
}