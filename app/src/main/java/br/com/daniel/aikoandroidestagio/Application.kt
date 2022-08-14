package br.com.daniel.aikoandroidestagio

import android.app.Application
import br.com.daniel.aikoandroidestagio.services.ApiModule

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        ApiModule.instancia(applicationContext)
    }

}