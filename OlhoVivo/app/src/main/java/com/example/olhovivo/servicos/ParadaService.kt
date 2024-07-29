package com.example.olhovivo.network

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import com.example.olhovivo.model.Parada
import java.io.IOException

class ParadaService {
    fun buscarParadasPorLinha(codigoLinha: Int, callback: (List<Parada>?) -> Unit) {
        val url = "http://api.olhovivo.sptrans.com.br/v2.1/Parada/BuscarParadasPorLinha?codigoLinha=$codigoLinha"
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
                    val paradas = parseParadas(responseData)
                    callback(paradas)
                } else {
                    callback(null)
                }
            }
        })
    }

    private fun parseParadas(responseData: String?): List<Parada> {
        // Implementar a lógica para converter a resposta JSON para uma lista de Parada
        // Use uma biblioteca de parsing JSON como Gson ou Moshi
    }
}
