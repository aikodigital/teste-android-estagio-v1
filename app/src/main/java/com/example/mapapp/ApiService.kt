package com.example.mapapp

import com.example.mapapp.ui.MyClass.AllPosition
import com.example.mapapp.ui.MyClass.ForecastResponse
import com.example.mapapp.ui.MyClass.LineData
import com.example.mapapp.ui.MyClass.StopPosition
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("${BuildConfig.BASE_URL}Login/Autenticar?token=${BuildConfig.API_KEY}")
    fun authenticate(): Call<Any>

    @GET("${BuildConfig.BASE_URL}Posicao")
    fun getAllLocation(@Header("Cookie") token: String): Call<AllPosition>

    @GET("${BuildConfig.BASE_URL}Linha/Buscar")
    fun getLine(@Header("Cookie") token: String, @Query("termosBusca") termosBusca: String): Call<List<LineData>>

    @GET("${BuildConfig.BASE_URL}Parada/Buscar")
    fun getStop(@Header("Cookie") token: String, @Query("termosBusca") termosBusca: String): Call<List<StopPosition>>

    @GET("${BuildConfig.BASE_URL}Previsao")
    fun getForecast(@Header("Cookie") token: String, @Query("codigoParada") codigoParada: String, @Query("codigoLinha") codigoLinha: String): Call<ForecastResponse>

    @GET("${BuildConfig.BASE_URL}Previsao/Parada")
    fun getForecastStop(@Header("Cookie") token: String, @Query("codigoParada") codigoParada: String): Call<ForecastResponse>

}