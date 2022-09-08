package com.andreesperanca.deolhonobus.di.modules

import androidx.room.Room
import com.andreesperanca.deolhonobus.data.local.FavoriteRoomDataBase
import com.andreesperanca.deolhonobus.data.local.daos.FavoriteDao
import com.andreesperanca.deolhonobus.data.remote.AppRetrofit
import com.andreesperanca.deolhonobus.data.remote.RetrofitService
import com.andreesperanca.deolhonobus.repositories.BusDetailsRepository
import com.andreesperanca.deolhonobus.repositories.BusStopDetailsRepository
import com.andreesperanca.deolhonobus.repositories.FavoriteRepository
import com.andreesperanca.deolhonobus.repositories.SearchRepository
import com.andreesperanca.deolhonobus.ui.viewmodels.BusDetailsViewModel
import com.andreesperanca.deolhonobus.ui.viewmodels.BusStopDetailsViewModel
import com.andreesperanca.deolhonobus.ui.viewmodels.FavoriteViewModel
import com.andreesperanca.deolhonobus.ui.viewmodels.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {

    single<FavoriteRoomDataBase> {
        Room.databaseBuilder(
            get(),
            FavoriteRoomDataBase::class.java,
            "favorite-db"
        ).build()
    }

    single<FavoriteDao> {
        get<FavoriteRoomDataBase>().favoriteDao
    }

    single<RetrofitService> {
        AppRetrofit().webService
    }

    single<SearchRepository> {
        SearchRepository(service = get(), get())
    }

    single<FavoriteRepository> {
        FavoriteRepository(get())
    }

    viewModel<SearchViewModel> {
        SearchViewModel(repository = get())
    }

    single<BusStopDetailsRepository> {
        BusStopDetailsRepository(service = get(),get())
    }

    viewModel<BusStopDetailsViewModel> {
        BusStopDetailsViewModel(repository = get())
    }

    single<BusDetailsRepository> {
        BusDetailsRepository(service = get(),get())
    }

    viewModel<BusDetailsViewModel> {
        BusDetailsViewModel(repository = get())
    }

    viewModel<FavoriteViewModel> {
        FavoriteViewModel(get())
    }
}