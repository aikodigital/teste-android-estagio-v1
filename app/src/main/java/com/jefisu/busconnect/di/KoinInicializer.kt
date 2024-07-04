package com.jefisu.busconnect.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class KoinInicializer(
    private val context: Context
) {
    fun init() {
        startKoin {
            androidContext(context)
            androidLogger()
            modules(mainModule, viewModelModule, remoteDateModule)
        }
    }
}