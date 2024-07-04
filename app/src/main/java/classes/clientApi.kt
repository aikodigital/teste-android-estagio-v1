package classes

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

val baseUrl: String = "https://aiko-olhovivo-proxy.aikodigital.io"
val token: String = "80f25bb9bf2ee17dd138c923911ed59d25634a2a250c5827cfeec54084670e03"

val client = HttpClient(Android) {
    install(Logging) {
        level = LogLevel.ALL
    }
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
        })
    }
}

suspend fun getParada(termo: String): List<Parada>
{
    val resposta: List<Parada> =
        client.get("$baseUrl/Parada/Buscar?termosBusca=$termo").body()

    return resposta
}

suspend fun getLinhas(termo: String): List<Linha>
{
    val resposta: List<Linha> =
        client.get("$baseUrl/Linha/Buscar?termosBusca=$termo").body()

    return resposta
}

suspend fun getPosicao(codigoLinha: String): Posicao
{
    val resposta: Posicao =
        client.get("$baseUrl/Posicao/Linha?codigoLinha=$codigoLinha").body()

    return resposta
}

suspend fun getParadas(termo: String): List<Parada>
{
    val resposta: List<Parada> =
        client.get("$baseUrl/Parada/BuscarParadasPorLinha?codigoLinha=$termo").body()

    return resposta
}

suspend fun getPrevisao(codigoParada: String, codigoLinha: String): Previsao
{
    val resposta: Previsao =
        client.get("$baseUrl/Previsao?codigoParada=$codigoParada&codigoLinha=$codigoLinha").body()

    return resposta
}


suspend fun validation(): HttpResponse
{
    val resposta: HttpResponse = client.post("$baseUrl/Login/Autenticar?token={$token}")

    return resposta
}

