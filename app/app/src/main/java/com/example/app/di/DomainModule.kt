package com.example.app.di

import com.example.app.data.repository.LineRepositoryImpl
import com.example.app.domain.repository.LineRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {
    @Binds
    abstract fun bindsLineRepositoryImpl(
        lineRepositoryImpl: LineRepositoryImpl,
    ): LineRepository
}