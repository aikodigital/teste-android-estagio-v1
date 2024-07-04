package com.exemple.urbanbus.di

import com.exemple.urbanbus.data.api.OlhoVivoAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// modulo do hilt para a criacao automatica da instancia do retrofit
@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Provides
    @Singleton
    fun provideRetrofitInstance(): Retrofit {
        val BASE_URL = "https://api.olhovivo.sptrans.com.br/v2.1/"
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideBusStopAPI(retrofit: Retrofit): OlhoVivoAPI {
        return retrofit.create(OlhoVivoAPI::class.java)
    }
}