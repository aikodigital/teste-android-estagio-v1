package com.conti.onibusspemtemporeal.data.retrofit

import android.content.Context
import android.net.ConnectivityManager
import com.conti.onibusspemtemporeal.R
import com.conti.onibusspemtemporeal.util.NetworkConnection
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class CheckNetworkInterceptor @Inject constructor(
    @ApplicationContext private val context: Context,
    private val connectivityManager: ConnectivityManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        if (!NetworkConnection(connectivityManager).checkConnection())
            throw NoInternetException()
        return chain.proceed(chain.request())
    }

    inner class NoInternetException : IOException() {
        override val message: String
            get() = context.resources.getString(R.string.connectivtyException)
    }

}

