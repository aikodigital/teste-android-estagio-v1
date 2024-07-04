package com.breno.testeandroidestagiov1


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import android.util.Log

suspend fun obterPosicoes(): String? {
    return withContext(Dispatchers.IO) {
        try {
            val url = URL("http://api.olhovivo.sptrans.com.br/v2.1/Posicao")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.doInput = true

            sessionCookie?.let {
                connection.setRequestProperty("Cookie", it)
            }

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                connection.inputStream.bufferedReader().readText()
            } else {
                Log.e("ObterPosicoesTask", "Erro ao obter posições. Código de resposta: $responseCode")
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("ObterPosicoesTask", "Erro ao obter posições: ${e.message}")
            null
        }
    }
}