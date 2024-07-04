package com.breno.testeandroidestagiov1

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import android.util.Log
import java.io.FileNotFoundException

var sessionCookie: String? = null

suspend fun autenticar(): Boolean {
    return withContext(Dispatchers.IO) {
        try {
            val token = "aaebaf5536320e8ac69d2ad69a6739c2bc384903cf7efda5868682375e403172"
            val url = URL("http://api.olhovivo.sptrans.com.br/v2.1/Login/Autenticar?token=$token")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.doOutput = true
            connection.doInput = true

            val writer = OutputStreamWriter(connection.outputStream)
            writer.write("")
            writer.flush()
            writer.close()

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val response = connection.inputStream.bufferedReader().readText().toBoolean()
                sessionCookie = connection.headerFields["Set-Cookie"]?.get(0)
                response
            } else {
                Log.e("AutenticarTask", "Erro na autenticação. Código de resposta: $responseCode")
                false
            }
        } catch (e: FileNotFoundException) {
            Log.e("AutenticarTask", "URL não encontrada: ${e.message}")
            false
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("AutenticarTask", "Erro na autenticação: ${e.message}")
            false
        }
    }
}