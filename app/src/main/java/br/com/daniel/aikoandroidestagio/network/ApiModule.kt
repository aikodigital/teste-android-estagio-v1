package br.com.daniel.aikoandroidestagio.network

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiModule() {

    companion object {
        @Volatile
        private lateinit var retrofit: Retrofit
        @Volatile
        private lateinit var keyApiOlho: String
        @Volatile
        lateinit var olhoAutentica: OlhoVivoAutenticarAPI
        @Volatile
        lateinit var olhoVivoServices: OlhoVivoAPI

        fun instancia(context: Context): Retrofit {
            if (::retrofit.isInitialized) return retrofit
            return Retrofit.Builder()
                .baseUrl("http://api.olhovivo.sptrans.com.br/v2.1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().also {
                    retrofit = it
                    pegaAPIKey(context)
                    criaServicesApi()
                }
        }

        private fun criaServicesApi() {
            olhoAutentica = retrofit.create(OlhoVivoAutenticarAPI::class.java)
            olhoVivoServices = retrofit.create(OlhoVivoAPI::class.java)
        }

        private fun pegaAPIKey(context: Context) {
            val ai: ApplicationInfo by lazy {
                context.packageManager
                    .getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
            }
            val value = ai.metaData["OLHO_API_KEY"]
            keyApiOlho = value.toString()
        }

        suspend fun autenticar() {
            olhoAutentica.autenticar(keyApiOlho)
        }

    }

}