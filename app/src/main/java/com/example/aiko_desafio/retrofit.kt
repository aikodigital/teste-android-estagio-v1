package com.example.aiko_desafio

import com.example.aiko_desafio.objetos.Chegada
import com.example.aiko_desafio.objetos.Paradas
import com.example.aiko_desafio.objetos.Linhas
import com.example.aiko_desafio.objetos.PosicaoVeiculos
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


class retrofit {
    // Objeto retrofit para ser instanciado em outras classes
    var retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://aiko-olhovivo-proxy.aikodigital.io/")
        .build().create(main::class.java)

}

// Interfaces para fazer as requisições necessarias
interface main{

    // Faz a requisição de autenticação via POST
    @POST("Login/Autenticar?token=00dbc4f2fdc3bbe7c3c0eaace850efce6647b8ec1893f2a5d3aaf745d8db0640")
    fun autentica(
    ): Call<Boolean>

    // Faz a requisição das posições dos veiculos via GET
    @GET("Posicao")
    fun getPosicao(
    ) : Call<PosicaoVeiculos>

    // Faz a requisição das posições das paradas via GET
    @GET("Parada/Buscar")
    fun getParadas(
        @Query("termosBusca") termos: String
    ): Call<List<Paradas>>

    // Faz a requisição das linhas via GET
    @GET("Linha/Buscar")
    fun getLinhas(
        @Query("termosBusca") termos: String
    ): Call<List<Linhas>>

    // Faz a requisição das linhas mas com o sentido
    @GET("/Linha/BuscarLinhaSentido")
    fun getLinhasSentido(
        @Query("termosBusca") termos: String,
        @Query("sentido") sentido: String
    ): Call<List<Linhas>>

    // Faz a requisição das previsões das paradas via GET
    @GET("/Previsao/Parada")
    fun getPrevisao(
        @Query("codigoParada") parada: String,
    ): Call<Chegada>


}