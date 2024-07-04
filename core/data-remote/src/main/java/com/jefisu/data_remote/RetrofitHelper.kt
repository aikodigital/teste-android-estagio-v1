package com.jefisu.data_remote

import net.gotev.cookiestore.okhttp.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.net.CookieManager

class RetrofitHelper(
    private val cookieManager: CookieManager
) {
    fun createApi(): SpTransApi {
        return Retrofit.Builder()
            .baseUrl(SpTransApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                    )
                    .cookieJar(JavaNetCookieJar(cookieManager))
                    .build()
            )
            .build()
            .create()
    }
}