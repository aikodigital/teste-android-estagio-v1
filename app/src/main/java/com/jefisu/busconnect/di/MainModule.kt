package com.jefisu.busconnect.di

import com.jefisu.common.network.ConnectivityObserver
import com.jefisu.common.network.NetworkConnectivityObserver
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val mainModule = module {
    singleOf(::NetworkConnectivityObserver).bind<ConnectivityObserver>()
    singleOf(Dispatchers::IO)
}