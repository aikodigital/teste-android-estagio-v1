package com.jefisu.busconnect.di

import com.jefisu.bus_stops.BusStopsViewModel
import com.jefisu.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::BusStopsViewModel)
}