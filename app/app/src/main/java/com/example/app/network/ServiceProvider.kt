package com.example.app.network

import com.example.app.BuildConfig
import com.example.app.data.api.ServiceAPI
import com.example.app.util.BASE_URL
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ServiceProvider {
    private var apiCredentials: String = ""

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .authenticator { _, response ->
            synchronized(this) {
                runBlocking {
                    val isAuthenticated = authenticate(BuildConfig.OLHO_VIVO_API_KEY)
                    if (isAuthenticated) {
                        response.request.newBuilder()
                            .header("Cookie", apiCredentials)
                            .build()
                    } else {
                        null
                    }
                }
            }
        }
        .build()

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun <API> createService(apiClass: Class<API>): API = retrofit.create(apiClass)

    private suspend fun authenticate(token: String): Boolean {
        val apiService = createService(ServiceAPI::class.java)
        return try {
            val response = apiService.authenticate(token)
            if (response.isSuccessful) {
                val headers = response.headers()
                val cookies = headers.values("Set-Cookie")
                apiCredentials = cookies.joinToString(", ")
                true
            } else {
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
