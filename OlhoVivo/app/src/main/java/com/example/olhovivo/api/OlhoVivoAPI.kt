package com.example.olhovivo.api

import com.example.olhovivo.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.QueryMap
import java.io.File

interface OlhoVivoAPI {
    @POST("Login/Autenticar")
    fun autenticar(@Query("token") token: String): Call<Boolean>

    @GET("Posicao")
    fun obterPosicoes(): Call<PosicoesResponse>

    @GET("Linha/Buscar")
    fun buscarLinhas(@Query("termosBusca") termosBusca: String): Call<List<Linha>>

    @GET("Parada/Buscar")
    fun buscarParadas(@Query("termosBusca") termosBusca: String): Call<List<Parada>>

    @GET("Parada/BuscarParadasPorLinha")
    fun buscarParadasPorLinha(@Query("codigoLinha") codigoLinha: Int): Call<List<Parada>>

    @GET("Previsao")
    fun obterPrevisaoChegada(@Query("codigoParada") codigoParada: Int, @Query("codigoLinha") codigoLinha: Int): Call<PrevisaoResponse>

    @GET("Previsao/Linha")
    fun obterPrevisaoPorLinha(@Query("codigoLinha") codigoLinha: Int): Call<PrevisaoResponse>

    @GET("Previsao/Parada")
    fun obterPrevisaoPorParada(@Query("codigoParada") codigoParada: Int): Call<PrevisaoResponse>

    @GET("Corredor/Obter")
    fun obterCorredores(): Call<List<Corredor>>

    @GET("Pesquisa")
    fun pesquisar(@Query("termo") term: String, @QueryMap filtros: Map<String, Any>): Call<List<ResultadoPesquisa>>

    @GET("Velocidade/Mapa")
    fun obterMapaVelocidade(@Query("sentido") sentido: String?): Call<File>
}
