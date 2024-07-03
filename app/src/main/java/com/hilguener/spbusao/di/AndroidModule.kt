package com.hilguener.spbusao.di

import com.google.gson.GsonBuilder
import com.hilguener.spbusao.data.http.OlhoVivoApi
import com.hilguener.spbusao.data.repository.HttpRepository
import com.hilguener.spbusao.data.util.API
import com.hilguener.spbusao.domain.usecase.AuthenticationUseCase
import com.hilguener.spbusao.domain.usecase.BusManagerUseCase
import com.hilguener.spbusao.domain.usecase.GetLinesUseCase
import com.hilguener.spbusao.domain.usecase.GetParadesByLineUseCase
import com.hilguener.spbusao.domain.usecase.GetParadesUseCase
import com.hilguener.spbusao.domain.usecase.GetPosVehiclesByLineUseCase
import com.hilguener.spbusao.domain.usecase.GetPosVehiclesUseCase
import com.hilguener.spbusao.domain.usecase.GetPrevArrivalUseCase
import com.hilguener.spbusao.presentation.map.MapsViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val androidModule =
    module {
        single { this }

        single {
            HttpRepository(api = get())
        }

        single {
            MapsViewModel(manager = get(), app = androidApplication())
        }

        single {
            AuthenticationUseCase(repo = get())
        }

        single {
            GetPosVehiclesUseCase(repo = get())
        }

        single {
            GetPosVehiclesByLineUseCase(repo = get())
        }

        single {
            GetParadesUseCase(repo = get())
        }

        single {
            GetParadesByLineUseCase(repo = get())
        }

        single {
            GetPrevArrivalUseCase(repo = get())
        }

        single {
            GetLinesUseCase(repo = get())
        }

        single {
            BusManagerUseCase(
                authenticate = get(),
                getPosVehicles = get(),
                getPosVehiclesByLineUseCase = get(),
                getParades = get(),
                getParadesByLineUseCase = get(),
                getPrevArrival = get(),
                getLines = get(),
            )
        }

        single {
            val httpClient = OkHttpClient.Builder()

            val gson =
                GsonBuilder()
                    .setLenient()
                    .create()

            val retrofit =
                Retrofit.Builder()
                    .baseUrl(API)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient.build())
                    .build()

            retrofit.create(OlhoVivoApi::class.java)
        }
    }
