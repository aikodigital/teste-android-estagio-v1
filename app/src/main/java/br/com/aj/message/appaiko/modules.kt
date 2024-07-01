package br.com.aj.message.appaiko

import android.content.Context
import br.com.aj.message.appaiko.repository.db.DatabaseRepository
import br.com.aj.message.appaiko.repository.db.MyDatabase
import br.com.aj.message.appaiko.repository.http.HttpRepository
import br.com.aj.message.appaiko.repository.http.Routes
import br.com.aj.message.appaiko.repository.http.MapFunctionsHttpRepository
import br.com.aj.message.appaiko.viewmodel.MapViewModel
import br.com.aj.message.appaiko.viewmodel.PrevFragmentViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val httpModulesRepo = module {
    single {  HttpRepository(get()) }
    single {  MapFunctionsHttpRepository(androidContext()) }
    viewModel { MapViewModel(get(),get(),get()) }
    viewModel { PrevFragmentViewModel(get()) }
}

val retrofitModules = module {
    single { createServiceHttp(androidContext(),"https://aiko-olhovivo-proxy.aikodigital.io/") }

}

val databaseModules = module {
    single { DatabaseRepository(MyDatabase.getInstancie(androidContext()) ) }

}

fun createServiceHttp(ctx:Context,urlBase:String): Routes {

    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY


    val  httpClientBuilder = OkHttpClient.Builder().addInterceptor(interceptor)
        .connectTimeout(2,TimeUnit.MINUTES)
        .readTimeout(20,TimeUnit.SECONDS)
        .cookieJar(Routes.Cookies(ctx))
        .build()

    return Retrofit.Builder()
        .client(httpClientBuilder)
        .baseUrl(urlBase)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Routes::class.java)
}


