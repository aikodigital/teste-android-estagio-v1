package com.aiko.aikospvision.application

import android.app.Application
import com.aiko.aikospvision.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AikoSPVisionApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidLogger()
            androidLogger(Level.ERROR)
            androidContext(this@AikoSPVisionApplication)
            modules(mainModule)
        }
    }
}