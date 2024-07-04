package com.jefisu.busconnect.di

import com.jefisu.data_remote.BusConnectRepositoryImpl
import com.jefisu.data_remote.RetrofitHelper
import com.jefisu.domain.BusConnectRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import java.net.CookieManager

val remoteDateModule = module {
    single { CookieManager() }
    single { RetrofitHelper(get()).createApi() }
    singleOf(::BusConnectRepositoryImpl).bind<BusConnectRepository>()
}