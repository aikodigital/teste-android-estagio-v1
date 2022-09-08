package com.andreesperanca.deolhonobus.application

import android.app.Application
import com.andreesperanca.deolhonobus.di.modules.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            modules(appModules)
        }
    }

}