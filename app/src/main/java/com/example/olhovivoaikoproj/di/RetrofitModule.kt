package com.example.olhovivoaikoproj.di

import com.example.olhovivoaikoproj.data.request.SpTransApi
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Headers

val retrofitModule = module {

    single {
        HttpLoggingInterceptor().apply { this.level = HttpLoggingInterceptor.Level.BODY }
    }

    single(named("INTERCEPTOR")) {
        Interceptor { chain ->
            val original: Request = chain.request()
            val originalHttpUrl: HttpUrl = original.url

            val url: HttpUrl = originalHttpUrl.newBuilder()
                .build()
            val requestBuilder = original.newBuilder()
                .url(url)

            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    single {
        OkHttpClient
            .Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(get<Interceptor>(named("INTERCEPTOR")))
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://aiko-olhovivo-proxy.aikodigital.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get<OkHttpClient>())
            .build()
    }

    single {
        get<Retrofit>().create(SpTransApi::class.java)
    }
}