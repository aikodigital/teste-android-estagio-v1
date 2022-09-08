package com.example.olhovivoaikoproj.data.request

import com.example.olhovivoaikoproj.data.response.Linha
import com.example.olhovivoaikoproj.data.response.Paradas
import com.example.olhovivoaikoproj.data.response.Posicao
import com.example.olhovivoaikoproj.data.response.PrevisaoParadaObj
import retrofit2.http.*

interface SpTransApi {

    @POST("Login/Autenticar?")
    suspend fun authenticateToken(@Query("token") token: String): Boolean

    @GET("Linha/Buscar")
    suspend fun getLinhas(@Query("termosBusca") termosBusca: String): List<Linha>

    @GET("/Parada/Buscar")
    suspend fun getParadas (@Query("termosBusca") termosBusca: String): List<Paradas>

    @GET("/Posicao/Linha")
    suspend fun getPosicaoLinha (@Query("codigoLinha") codigoLinha: String): List<Posicao>

    @GET("/Previsao/Parada")
    suspend fun getPrevisao(@Query("codigoParada") codigoParada: String): PrevisaoParadaObj

}