package br.com.aiko.estagio.bussp.data.remote

import br.com.aiko.estagio.bussp.data.remote.response.Linha
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TransService {

    @POST("/Login/Autenticar")
    suspend fun authentication(@Query("token") token: String): Response<Boolean>

    @GET("/Linha/Buscar")
    suspend fun buscarLinha(@Query("termosBusca") linha: String): Response<List<Linha>>

    @GET("/Linha/BuscarLinhaSentido")
    suspend fun buscarLinhaSentido(
        @Query("termosBusca") linha: String,
        @Query("sentido") sentido: Int
    ): Response<List<Linha>>

}