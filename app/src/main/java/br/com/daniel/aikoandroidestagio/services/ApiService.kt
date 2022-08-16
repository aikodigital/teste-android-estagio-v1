package br.com.daniel.aikoandroidestagio.services

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import br.com.daniel.aikoandroidestagio.model.LocalizacaoVeiculos
import br.com.daniel.aikoandroidestagio.model.Parada
import br.com.daniel.aikoandroidestagio.model.PrevisaoChegada
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiService() {

    companion object {

        private lateinit var keyApiOlho: String
        private var certificacao: String = ""

        @Volatile
        private lateinit var retrofit: Retrofit

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
            val ai: ApplicationInfo = context.packageManager
                .getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
            val value = ai.metaData["OLHO_API_KEY"]
            keyApiOlho = value.toString()
        }

        suspend fun autenticar(): Response<Boolean> {
            return olhoAutentica.autenticar(keyApiOlho)
        }

        suspend fun getPosicoes(): Response<LocalizacaoVeiculos> {
            return olhoVivoServices.getPosicoes(certificacao)
        }

        suspend fun getParadas(nomeRua: String): Response<List<Parada>> {
            return olhoVivoServices.getParada(certificacao, nomeRua)
        }

        suspend fun getPrevisao(id: Int): Response<PrevisaoChegada> {
            return olhoVivoServices.getPrevisao(certificacao, id)
        }

        fun setCookie(cookie: String) {
            certificacao = cookie
        }
    }
}
