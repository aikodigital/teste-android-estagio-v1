package br.com.daniel.aikoandroidestagio

import android.app.Application
import android.util.Log
import br.com.daniel.aikoandroidestagio.network.ApiModule

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        ApiModule.instancia(applicationContext)
    }

}