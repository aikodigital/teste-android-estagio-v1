package com.conti.onibusspemtemporeal.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import androidx.room.RoomDatabase
import com.conti.onibusspemtemporeal.data.retrofit.CheckNetworkInterceptor
import com.conti.onibusspemtemporeal.data.retrofit.ManagerCookiesWithCookieJar
import com.conti.onibusspemtemporeal.data.retrofit.interfaces.services.OlhoVivoApiAuthenticateServiceInterface
import com.conti.onibusspemtemporeal.data.retrofit.interfaces.services.OlhoVivoApiServiceInterface
import com.conti.onibusspemtemporeal.data.room.BusDao
import com.conti.onibusspemtemporeal.data.room.BusDataSourceImpl
import com.conti.onibusspemtemporeal.data.room.BusDataSourceInterface
import com.conti.onibusspemtemporeal.data.room.BusDatabase
import com.conti.onibusspemtemporeal.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun providesBusDao(database: BusDatabase): BusDao {
        return database.busDao()
    }


    @Singleton
    @Provides
    fun providesBusDatabase(
        @ApplicationContext app: Context
    ): BusDatabase {
        return Room.databaseBuilder(
            app,
            BusDatabase::class.java,
            "bus_database"
        ).build()
    }

    @Singleton
    @BusRoomDataSource
    @Provides
    fun providesRoomBusDataSource(database: BusDatabase): BusDataSourceInterface {
        return BusDataSourceImpl(database.busDao())
    }

    @Singleton
    @Provides
    fun providesRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Provides
    fun providesManagerCookies(): ManagerCookiesWithCookieJar {
        return ManagerCookiesWithCookieJar()
    }

    @Provides
    fun providesConnectivityManager(
        @ApplicationContext context: Context
    ): ConnectivityManager {
        return context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
    }

    @Provides
    fun providesNetworkCheck(
        @ApplicationContext context: Context,
        connectivityManager: ConnectivityManager
    ): CheckNetworkInterceptor {
        return CheckNetworkInterceptor(context, connectivityManager)
    }

    @Singleton
    @Provides
    fun providesOkHttpClientWithCookieJar(
        managerCookieJar: ManagerCookiesWithCookieJar,
        checkNetworkInterceptor: CheckNetworkInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(checkNetworkInterceptor)
            .cookieJar(managerCookieJar).build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(
        retrofit: Retrofit.Builder,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return retrofit.client(okHttpClient).build()
    }

    @Singleton
    @Provides
    fun providesOlhoVivoAuthenticateService(retrofit: Retrofit): OlhoVivoApiAuthenticateServiceInterface {
        return retrofit.create(OlhoVivoApiAuthenticateServiceInterface::class.java)
    }

    @Singleton
    @Provides
    fun providesOlhoVivoService(retrofit: Retrofit): OlhoVivoApiServiceInterface {
        return retrofit.create(OlhoVivoApiServiceInterface::class.java)
    }

}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class BusRoomDataSource