package br.com.daniel.aikoandroidestagio.services

import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface OlhoVivoAutenticarAPI {
    //Pegar o cookie e usar como certficado nos gets
    @POST("Login/Autenticar")
    suspend fun autenticar(@Query("token") apiKey: String): Response<Boolean?>
}