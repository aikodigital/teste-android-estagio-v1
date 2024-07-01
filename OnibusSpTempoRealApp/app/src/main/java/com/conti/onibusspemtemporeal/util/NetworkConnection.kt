package com.conti.onibusspemtemporeal.util

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkConnection (
    private val connectivityManager: ConnectivityManager
) {
    @SuppressLint("MissingPermission")
    fun checkConnection(): Boolean {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val connection = connectivityManager.getNetworkCapabilities(network)
            return connection != null && (
                    connection.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            connection.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
        } else {
            val activeNetwork = connectivityManager.activeNetworkInfo
            if (activeNetwork != null) {
                return (activeNetwork.type == ConnectivityManager.TYPE_WIFI ||
                        activeNetwork.type == ConnectivityManager.TYPE_MOBILE)
            }
            return false
        }
    }
}