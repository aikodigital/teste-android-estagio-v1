package br.com.daniel.aikoandroidestagio.services

import br.com.daniel.aikoandroidestagio.model.Linha
import br.com.daniel.aikoandroidestagio.model.LocalizacaoVeiculos
import br.com.daniel.aikoandroidestagio.model.Parada
import br.com.daniel.aikoandroidestagio.model.PrevisaoChegada
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface OlhoVivoAPI {
    @GET("Linha/Buscar")
    suspend fun getLinha(@Header("Cookie") credencial: String, @Query("termosBusca") codLinha: String): List<Linha>?

    @GET("Posicao")
    suspend fun getPosicoes(@Header("Cookie") credencial: String): Response<LocalizacaoVeiculos>

    @GET("Parada/Buscar")
    suspend fun getParada(@Header("Cookie") credencial: String, @Query("termosBusca") nomeRua: String): Response<List<Parada>>

    @GET("Previsao/Parada")
    suspend fun getPrevisao(@Header("Cookie") certificacao: String, @Query("codigoParada") id: Int): Response<PrevisaoChegada>
}