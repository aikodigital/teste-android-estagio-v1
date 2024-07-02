package br.com.samuel.testeaiko.di

import br.com.samuel.testeaiko.util.OLHO_VIVO_URL
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RetrofitModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val mapper = ObjectMapper()
        mapper.registerModules(JavaTimeModule())

        return Retrofit.Builder()
            .baseUrl(OLHO_VIVO_URL)
            .addConverterFactory(JacksonConverterFactory.create(mapper))
            .build()
    }

}