package br.com.daniel.aikoandroidestagio.network

import retrofit2.http.POST
import retrofit2.http.Query

interface OlhoVivoAutenticarAPI {
    @POST("/Login/Autenticar")
    suspend fun autenticar(@Query("token") apiKey: String): Boolean?
}