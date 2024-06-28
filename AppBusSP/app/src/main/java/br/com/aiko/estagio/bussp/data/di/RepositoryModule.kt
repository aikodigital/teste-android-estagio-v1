package br.com.aiko.estagio.bussp.data.di

import br.com.aiko.estagio.bussp.data.remote.RemoteDataSource
import br.com.aiko.estagio.bussp.data.remote.TransService
import br.com.aiko.estagio.bussp.data.repository.TransRepository
import br.com.aiko.estagio.bussp.data.repository.TransRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {
    @Binds
    fun bindTransRepository(repositoryImpl: TransRepositoryImpl): TransRepository
}

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataSourceModule {
    @Provides
    fun provideRemoteDataSource(service: TransService): RemoteDataSource {
        return RemoteDataSource(service)
    }
}