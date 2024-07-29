package com.example.olhovivo.network

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import com.example.olhovivo.model.Linha
import java.io.IOException

class LinhaService {
    fun buscarLinhas(termosBusca: String, callback: (List<Linha>?) -> Unit) {
        val url = "http://api.olhovivo.sptrans.com.br/v2.1/Linha/Buscar?termosBusca=$termosBusca"
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
                    val linhas = parseLinhas(responseData)
                    callback(linhas)
                } else {
                    callback(null)
                }
            }
        })
    }

    private fun parseLinhas(responseData: String?): List<Linha> {
        // Implementar a lógica para converter a resposta JSON para uma lista de Linha
        // Use uma biblioteca de parsing JSON como Gson ou Moshi
    }
}
