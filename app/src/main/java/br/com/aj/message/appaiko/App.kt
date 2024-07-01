package br.com.aj.message.appaiko

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            modules(databaseModules,retrofitModules, httpModulesRepo)
            androidContext(this@App)
        }
    }
}