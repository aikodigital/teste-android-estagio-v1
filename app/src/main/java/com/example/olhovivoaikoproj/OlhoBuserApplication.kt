package com.example.olhovivoaikoproj

import android.app.Application
import com.example.olhovivoaikoproj.di.adapterModule
import com.example.olhovivoaikoproj.di.repositoryModule
import com.example.olhovivoaikoproj.di.retrofitModule
import com.example.olhovivoaikoproj.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class OlhoBuserApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@OlhoBuserApplication)
            modules(listOf(retrofitModule, repositoryModule, viewModelModule, adapterModule))
        }

    }
}