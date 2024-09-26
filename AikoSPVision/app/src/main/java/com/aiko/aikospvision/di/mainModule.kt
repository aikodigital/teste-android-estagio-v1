package com.aiko.aikospvision.di

import com.aiko.aikospvision.ui.screens_app.bus_lines.ViewModelBusLine
import com.aiko.aikospvision.ui.screens_app.bus_lines.ViewModelBusLineImpl
import com.aiko.aikospvision.ui.screens_app.home.ViewModelHome
import com.aiko.aikospvision.ui.screens_app.home.ViewModelHomeImpl
import com.aiko.aikospvision.ui.screens_app.stops.ViewModelStops
import com.aiko.aikospvision.ui.screens_app.stops.ViewModelStopsImpl
import com.aiko.aikospvision.ui.screens_app.veicle_position.ViewModelVehiclePosition
import com.aiko.aikospvision.ui.screens_app.veicle_position.ViewModelVehiclePositionImpl
import com.aiko.data.remote.VisionService
import com.aiko.data.remote.adapters.internal.NetworkResultCallAdapterFactory
import com.aiko.data.repository.SPVisionRepositoryImpl
import com.aiko.domain.repository.SPVisionRepository
import com.aiko.domain.usecase.GetForecastUseCase
import com.aiko.domain.usecase.GetLineBySearchTermUseCase
import com.aiko.domain.usecase.GetStopsBySearchTermUseCase
import com.aiko.domain.usecase.PostAuthUseCase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import net.gotev.cookiestore.okhttp.JavaNetCookieJar
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.CookieManager


val mainModule = module {

    factory { PostAuthUseCase(get()) }
    factory { GetStopsBySearchTermUseCase(get()) }
    factory { GetForecastUseCase(get()) }
    factory { GetLineBySearchTermUseCase(get()) }

    single<SPVisionRepository> { SPVisionRepositoryImpl(get()) }

    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single<VisionService> { get<Retrofit>().create(VisionService::class.java) }

    single {
        val cookieManager = CookieManager()
        val client = OkHttpClient.Builder()
            .cookieJar(JavaNetCookieJar(cookieManager))
            .build()
        Retrofit.Builder()
            .baseUrl("https://api.olhovivo.sptrans.com.br/v2.1/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .addCallAdapterFactory(NetworkResultCallAdapterFactory.create())
            .build()
    }

    viewModel<ViewModelHome> { ViewModelHomeImpl(get(), get()) }
    viewModel<ViewModelStops> { ViewModelStopsImpl(get(), get()) }
    viewModel<ViewModelVehiclePosition> { ViewModelVehiclePositionImpl() }
    viewModel<ViewModelBusLine> { ViewModelBusLineImpl(get()) }
}