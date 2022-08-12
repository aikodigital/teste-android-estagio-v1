package br.com.daniel.aikoandroidestagio.network

import br.com.daniel.aikoandroidestagio.model.Linha
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface OlhoVivoAPI {
    @GET("/Linha/Buscar")
    suspend fun getLinha(@Query("termosBusca") codLinha: String): List<Linha>?
}