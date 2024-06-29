package br.com.aiko.estagio.bussp.data.remote

import br.com.aiko.estagio.bussp.data.remote.response.Corredor
import br.com.aiko.estagio.bussp.data.remote.response.Empresas
import br.com.aiko.estagio.bussp.data.remote.response.Linha
import br.com.aiko.estagio.bussp.data.remote.response.Parada
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

    @GET("/Parada/Buscar")
    suspend fun buscarParada(@Query("termosBusca") parada: String): Response<List<Parada>>

    @GET("/Parada/BuscarParadasPorLinha")
    suspend fun buscarParadasPorLinha(@Query("codigoLinha") codigoLinha: String): Response<List<Parada>>

    @GET("/Parada/BuscarParadasPorCorredor")
    suspend fun buscarParadasPorCorredor(@Query("codigoCorredor") codigoCorredor: Int): Response<List<Parada>>

    @GET("/Corredor")
    suspend fun corredor(): Response<List<Corredor>>

    @GET("/Empresa")
    suspend fun empresas(): Response<Empresas>

}