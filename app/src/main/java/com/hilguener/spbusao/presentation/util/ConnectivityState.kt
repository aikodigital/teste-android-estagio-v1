package com.hilguener.spbusao.presentation.util

import android.net.ConnectivityManager
import android.net.Network
import android.os.Build

fun ConnectivityManager.networkCallback(
    callbackOnAvailable: () -> Unit,
    callbackOnLost: () -> Unit,
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        this.registerDefaultNetworkCallback(
            object :
                ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)

                    callbackOnAvailable()
                }

                override fun onLost(network: Network) {
                    super.onLost(network)

                    callbackOnLost()
                }
            },
        )
    }
}

fun ConnectivityManager.haveInternetOnInitApp(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        this.run {
            this.activeNetwork != null && this.getNetworkCapabilities(this.activeNetwork) != null
        }
    } else {
        val info = this.activeNetworkInfo
        info != null && info.isConnected
    }
}
