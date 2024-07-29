package com.example.teste.network

import com.example.teste.models.ArrivalPredictionResponse
import com.example.teste.models.BusLine
import com.example.teste.models.Stop
import com.example.teste.models.VehiclePositionsResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor

object OlhoVivoService {
    private const val BASE_URL = "https://api.olhovivo.sptrans.com.br/v2.1"
    private const val API_KEY = "3ab10c767025f88444538d04616e28719bc52567942171da71db8942fa2954ac"

    var authCookie: String? = null

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()

            authCookie?.let {
                requestBuilder.addHeader("Cookie", it)
            }

            val request = requestBuilder.build()
            val response = chain.proceed(request)

            if (original.url.toString().contains("/Login/Autenticar")) {
                response.headers("Set-Cookie").forEach {
                    if (it.startsWith("apiCredentials")) {
                        authCookie = it
                    }
                }
            }

            response
        }
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    fun authenticate(): Boolean {
        val request = Request.Builder()
            .url("$BASE_URL/Login/Autenticar?token=$API_KEY")
            .post(FormBody.Builder().build())
            .build()

        client.newCall(request).execute().use { response ->
            return response.isSuccessful
        }
    }

    fun getBusPositions(): VehiclePositionsResponse? {
        val request = Request.Builder()
            .url("$BASE_URL/Posicao")
            .get()
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                return null
            }
            val responseBody = response.body?.string() ?: return null
            return Gson().fromJson(responseBody, VehiclePositionsResponse::class.java)
        }
    }

    fun searchLines(termosBusca: String): List<BusLine>? {
        if (authCookie == null) {
            val isAuthenticated = authenticate()
            if (!isAuthenticated) {
                return null
            }
        }

        val request = Request.Builder()
            .url("$BASE_URL/Linha/Buscar?termosBusca=$termosBusca")
            .get()
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                return null
            }
            val responseBody = response.body?.string() ?: return null
            val listType = object : TypeToken<List<BusLine>>() {}.type
            return Gson().fromJson(responseBody, listType)
        }
    }

    fun getStopsByLine(lineCode: Int): List<Stop>? {
        if (authCookie == null) {
            val isAuthenticated = authenticate()
            if (!isAuthenticated) {
                return null
            }
        }

        val request = Request.Builder()
            .url("$BASE_URL/Parada/BuscarParadasPorLinha?codigoLinha=$lineCode")
            .get()
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                return null
            }
            val responseBody = response.body?.string() ?: return null
            val listType = object : TypeToken<List<Stop>>() {}.type
            return Gson().fromJson(responseBody, listType)
        }
    }

    fun getArrivalPrediction(codigoParada: Int, codigoLinha: Int): ArrivalPredictionResponse? {
        val url = "$BASE_URL/Previsao?codigoParada=$codigoParada&codigoLinha=$codigoLinha"

        val request = Request.Builder()
            .url(url)
            .addHeader("Cookie", authCookie ?: "")
            .build()

        return try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val responseBody = response.body?.string()
                responseBody?.let {
                    Gson().fromJson(it, ArrivalPredictionResponse::class.java)
                }
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

}
