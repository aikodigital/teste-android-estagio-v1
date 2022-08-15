package com.example.spTransAiko2.model

import com.example.spTransAiko2.data.Posicao
import retrofit2.Call
import retrofit2.http.*

interface apiOlhoVivo {
    companion object {
        const val baseUrl = "http://api.olhovivo.sptrans.com.br/v2.1/"
    }

    @POST("Login/Autenticar")
    fun getLogin(@Query("token") token: String?): Call<String>

    @GET("Posicao/")
    //fun getPosicao(@Header("Authorization") cookie: String) :Call<Posicao>
    fun getPosicao(
        @Header("cookie") cookie: String?
    ): Call<Posicao?>?
}
