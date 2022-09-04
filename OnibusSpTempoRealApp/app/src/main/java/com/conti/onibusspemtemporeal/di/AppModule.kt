package com.conti.onibusspemtemporeal.di

import com.conti.onibusspemtemporeal.data.retrofit.ManagerCookiesWithCookieJar
import com.conti.onibusspemtemporeal.data.retrofit.interfaces.services.OlhoVivoApiAuthenticateServiceInterface
import com.conti.onibusspemtemporeal.data.retrofit.interfaces.services.OlhoVivoApiServiceInterface
import com.conti.onibusspemtemporeal.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun providesRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Provides
    fun providesManagerCookies(): ManagerCookiesWithCookieJar {
        return ManagerCookiesWithCookieJar()
    }

    @Singleton
    @Provides
    fun providesOkHttpClientWithCookieJar(
        managerCookieJar: ManagerCookiesWithCookieJar
    ): OkHttpClient {
        return OkHttpClient.Builder().cookieJar(managerCookieJar).build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(
        retrofit: Retrofit.Builder,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return retrofit.client(okHttpClient).build()
    }

    @Singleton
    @Provides
    fun providesOlhoVivoAuthenticateService(retrofit: Retrofit): OlhoVivoApiAuthenticateServiceInterface {
        return retrofit.create(OlhoVivoApiAuthenticateServiceInterface::class.java)
    }

    @Singleton
    @Provides
    fun providesOlhoVivoService(retrofit: Retrofit): OlhoVivoApiServiceInterface {
        return retrofit.create(OlhoVivoApiServiceInterface::class.java)
    }
}