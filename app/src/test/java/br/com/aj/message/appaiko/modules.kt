package br.com.aj.message.appaiko

import br.com.aj.message.appaiko.repository.http.HttpRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val httpModulesRepo = module {
    single {  HttpRepository(get()) }
}

val retrofitModules = module {
    single { createServiceHttp(androidContext(),"https://aiko-olhovivo-proxy.aikodigital.io/") }

}




