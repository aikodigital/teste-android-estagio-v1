package com.jefisu.busconnect

import android.app.Application
import com.jefisu.busconnect.di.KoinInicializer

class BusConnectApp : Application() {
    override fun onCreate() {
        super.onCreate()
        KoinInicializer(this).init()
    }
}