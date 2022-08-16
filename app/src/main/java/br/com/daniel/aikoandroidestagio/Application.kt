package br.com.daniel.aikoandroidestagio

import android.app.Application
import br.com.daniel.aikoandroidestagio.services.ApiService

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        ApiService.instancia(applicationContext)
    }

}