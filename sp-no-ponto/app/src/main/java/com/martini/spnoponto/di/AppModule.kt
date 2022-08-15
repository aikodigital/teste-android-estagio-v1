package com.martini.spnoponto.di

import android.content.Context
import com.martini.spnoponto.constants.Constants
import com.martini.spnoponto.data.dataSources.local.SettingsLocalDataSource
import com.martini.spnoponto.data.dataSources.remote.TrafficService
import com.martini.spnoponto.data.repositories.TrafficRepositoryImpl
import com.martini.spnoponto.domain.entities.json.JsonConverterLinha
import com.martini.spnoponto.domain.repositories.TrafficRepository
import com.martini.spnoponto.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.HEADERS
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.apiBaseUrl)
            .build()
    }

    @Provides
    @Singleton
    fun providesTrafficService(
        retrofit: Retrofit
    ): TrafficService {
        return retrofit.create(TrafficService::class.java)
    }

    @Provides
    @Singleton
    fun providesTrafficRepository(
        trafficService: TrafficService,
        settingsLocalDataSource: SettingsLocalDataSource
    ): TrafficRepository {
        return TrafficRepositoryImpl(
            trafficService,
            settingsLocalDataSource
        )
    }

    @Provides
    @Singleton
    fun provideLineSearchUseCase(
        trafficRepository: TrafficRepository
    ): SearchLineUseCase {
        return SearchLineUseCase(trafficRepository)
    }

    @Provides
    @Singleton
    fun providesNavigationUseCase(
        jsonConverterLinha: JsonConverterLinha
    ): NavigationUseCase {
        return NavigationUseCase(jsonConverterLinha)
    }

    @Provides
    @Singleton
    fun providesLineDetailsUseCase(
        jsonConverterLinha: JsonConverterLinha
    ): LineDetailsUseCase {
        return LineDetailsUseCase(jsonConverterLinha)
    }

    @Provides
    @Singleton
    fun providesGetLinePositionUseCase(
        trafficRepository: TrafficRepository
    ): GetLinePositionUseCase {
        return GetLinePositionUseCase(trafficRepository)
    }

    @Provides
    @Singleton
    fun providesGetBusStopUseCase(
        trafficRepository: TrafficRepository
    ): GetBusStopUseCase {
        return GetBusStopUseCase(trafficRepository)
    }

    @Provides
    @Singleton
    fun providesJsonConverterLinha(): JsonConverterLinha {
        return JsonConverterLinha()
    }

    @Provides
    @Singleton
    fun providesGetBusStopForecastUseCase(
        trafficRepository: TrafficRepository
    ): GetBusStopForecastUseCase {
        return GetBusStopForecastUseCase(trafficRepository)
    }

    @Provides
    @Singleton
    fun providesSettingsLocalDataSource(
        @ApplicationContext context: Context
    ): SettingsLocalDataSource {
        val prefs = context.getSharedPreferences(Constants.sharedPrefsKey, Context.MODE_PRIVATE)
        return SettingsLocalDataSource(prefs)
    }

    @Provides
    @Singleton
    fun providesGetFilterUseCase(
        trafficRepository: TrafficRepository
    ): GetFilterSettingUseCase {
        return GetFilterSettingUseCase(trafficRepository)
    }

    @Provides
    @Singleton
    fun providesSetFilterSettingsUseCase(
        trafficRepository: TrafficRepository
    ): SetFilterSettingsUseCase {
        return SetFilterSettingsUseCase(trafficRepository)
    }
}