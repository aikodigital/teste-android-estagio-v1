package br.com.aiko.estagio.bussp.data.remote

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface TransService {

    @POST("/Login/Autenticar")
    suspend fun authentication(@Query("token") token: String): Response<Boolean>

}