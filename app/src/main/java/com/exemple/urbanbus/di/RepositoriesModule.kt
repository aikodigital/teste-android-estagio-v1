package com.exemple.urbanbus.di

import com.exemple.urbanbus.data.api.OlhoVivoAPI
import com.exemple.urbanbus.data.repositories.authenticate.AuthenticateRepository
import com.exemple.urbanbus.data.repositories.authenticate.AuthenticateRepositoryImp
import com.exemple.urbanbus.data.repositories.lines.BusLineRepository
import com.exemple.urbanbus.data.repositories.lines.BusLineRepositoryImp
import com.exemple.urbanbus.data.repositories.stops.BusStopRepository
import com.exemple.urbanbus.data.repositories.stops.BusStopRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// modulo do hilt para a criacao das instancias de cada repository
@Module
@InstallIn(SingletonComponent::class)
class RepositoriesModule {
    @Provides
    @Singleton
    fun provideAuthenticateRepository(api: OlhoVivoAPI): AuthenticateRepository {
        return AuthenticateRepositoryImp(api)
    }

    @Provides
    @Singleton
    fun provideBusStopRepository(
        auth: AuthenticateRepository,
        api: OlhoVivoAPI
    ): BusStopRepository {
        return BusStopRepositoryImp(auth, api)
    }

    @Provides
    @Singleton
    fun provideBusLineRepository(
        auth: AuthenticateRepository,
        api: OlhoVivoAPI
    ): BusLineRepository {
        return BusLineRepositoryImp(auth, api)
    }

}