package br.com.samuel.testeaiko.di

import br.com.samuel.testeaiko.core.application.repositories.ArrivalForecastRepository
import br.com.samuel.testeaiko.core.application.repositories.AuthRepository
import br.com.samuel.testeaiko.core.application.repositories.CorridorsRepository
import br.com.samuel.testeaiko.core.application.repositories.LinesRepository
import br.com.samuel.testeaiko.core.application.repositories.PositionsRepository
import br.com.samuel.testeaiko.core.application.repositories.StopsRepository
import br.com.samuel.testeaiko.infra.repositories.OlhoVivoArrivalForecastRepositoryImpl
import br.com.samuel.testeaiko.infra.repositories.OlhoVivoAuthRepositoryImpl
import br.com.samuel.testeaiko.infra.repositories.OlhoVivoCorridorsRepositoryImpl
import br.com.samuel.testeaiko.infra.repositories.OlhoVivoLinesRepositoryImpl
import br.com.samuel.testeaiko.infra.repositories.OlhoVivoPositionsRepositoryImpl
import br.com.samuel.testeaiko.infra.repositories.OlhoVivoStopsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
internal object OlhoVivoRepository {

    @Provides
    fun provideAuthRepository(retrofit: Retrofit): AuthRepository =
        OlhoVivoAuthRepositoryImpl(retrofit)

    @Provides
    fun provideLinesRepository(retrofit: Retrofit): LinesRepository =
        OlhoVivoLinesRepositoryImpl(retrofit)

    @Provides
    fun provideStopsRepository(retrofit: Retrofit): StopsRepository =
        OlhoVivoStopsRepositoryImpl(retrofit)

    @Provides
    fun providePositionsRepository(retrofit: Retrofit): PositionsRepository =
        OlhoVivoPositionsRepositoryImpl(retrofit)

    @Provides
    fun provideArrivalForecastRepository(retrofit: Retrofit): ArrivalForecastRepository =
        OlhoVivoArrivalForecastRepositoryImpl(retrofit)

    @Provides
    fun provideCorridorsRepository(retrofit: Retrofit): CorridorsRepository =
        OlhoVivoCorridorsRepositoryImpl(retrofit)

}