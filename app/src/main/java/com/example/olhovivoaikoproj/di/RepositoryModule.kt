package com.example.olhovivoaikoproj.di

import com.example.olhovivoaikoproj.domain.SpTransRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory { SpTransRepository(spTransApi = get()) }
}