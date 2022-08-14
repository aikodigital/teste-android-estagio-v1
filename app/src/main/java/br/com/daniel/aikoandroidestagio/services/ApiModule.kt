package br.com.daniel.aikoandroidestagio.services

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import br.com.daniel.aikoandroidestagio.model.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiModule() {

    companion object {
        @Volatile
        private lateinit var retrofit: Retrofit

        @Volatile
        private lateinit var keyApiOlho: String

        var certificacao: String = ""

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

        suspend fun autenticar(): Response<Boolean?> {
            return olhoAutentica.autenticar(keyApiOlho)
        }

        fun criaLinhasFake(): List<Linha> {
            return listOf(
                Linha(841, false, "118Y", 1, 10, "LAPA", "LAUZANE PAULISTA"),
                Linha(33609, false, "118Y", 2, 10, "LAPA", "LAUZANE PAULISTA"),
                Linha(841, false, "119L", 1, 1, "TERM. LAPA", "VL. SULINA"),
                Linha(841, false, "119L", 2, 1, "TERM. LAPA", "VL. SULINA")
            )
        }

        fun criaParadasFake(): List<Parada> {
            return listOf(
                Parada(340015333, "AFONSO BRAZ C/B1",
                    "R DOUTORA MARIA AUGUSTA SARAIVA/ R NATIVIDADE", -23.59572, -46.673285),
                Parada(340015331, "AFONSO BRAZ C/B1",
                    "R DOUTORA MARIA AUGUSTA SARAIVA/ R NATIVIDADE", -23.595087, -46.673152),
                Parada(340015329, "PARADA 1 - AFONSO BRAZ B/C",
                    "R ARMINDA/ R BALTHAZAR DA VEIGA", -23.592938, -46.672727),
                Parada(340015328, "PARADA 2 - AFONSO BRAZ B/C",
                    "R ARMINDA/ R BALTHAZAR DA VEIGA", -23.59337, -46.672766),
            )
        }

        fun criaPosicaoFake(): LocalizacaoVeiculos {
            return LocalizacaoVeiculos("10:50", listOf(
                L("1760-10", 33325,2,"SHOP. CENTER NORTE","COHAB ANTÁRTICA",3, listOf(
                    V(22531, true, "2022-08-14T01:36:15Z",-23.4571605,-46.6597545),
                    V(22287, true, "2022-08-14T01:36:07Z",-23.457146,-46.659533249999996),
                    V(22508, true, "2022-08-14T01:36:24Z",-23.497252500000002,-46.627555)
                )),
                L("971R-10", 614,1,"METRÔ SANTANA","CPTM JARAGUÁ",10, listOf(
                    V(21490, true, "2022-08-14T01:36:30Z",-23.472089625,-46.673646500000004),
                    V(21989, true, "2022-08-14T01:36:21Z",-23.440492,-46.7082695),
                    V(21987, true, "2022-08-14T01:36:20Z",-23.473402749999998,-46.671382749999992)
                ))
            ))
        }

        suspend fun getPosicoes(): Response<LocalizacaoVeiculos> {
            return olhoVivoServices.getPosicoes(certificacao)
        }

        fun setCookie(cookie: String) {
            certificacao = cookie
        }
    }
}
