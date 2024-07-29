package com.example.olhovivo.network

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import com.example.olhovivo.model.PrevisaoResponse
import java.io.IOException

class PrevisaoService {
    fun previsaoChegada(codigoParada: Int, codigoLinha: Int, callback: (PrevisaoResponse?) -> Unit) {
        val url = "http://api.olhovivo.sptrans.com.br/v2.1/Previsao?codigoParada=$codigoParada&codigoLinha=$codigoLinha"
        val request = Request.Builder()
            .url(url)
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback(null)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseData = response.body?.string()
                    val previsao = parsePrevisao(responseData)
                    callback(previsao)
                } else {
                    callback(null)
                }
            }
        })
    }

    private fun parsePrevisao(responseData: String?): PrevisaoResponse {
        // Implementar a lógica para converter a resposta JSON para PrevisaoResponse
        // Use uma biblioteca de parsing JSON como Gson ou Moshi
    }
}
