package br.com.aj.message.appaiko.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

/**
 * Classe responsavel pela a obeservação da internet
 */
class ConnectionObserver(ctx: Context) : ConnectionManager {

    private val inter : ConnectivityManager = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun observe(): Flow<ConnectionManager.Status> {

      return  callbackFlow {

            val ob = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch { send(ConnectionManager.Status.CONNECTED) }

                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch { send(ConnectionManager.Status.NOT_CONNECTED) }
                }
            }

          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
              inter.registerDefaultNetworkCallback(ob)
          }else{
              val networkChangeFilter = NetworkRequest.Builder().build()
              inter.registerNetworkCallback(networkChangeFilter,ob)
          }
          awaitClose {
              inter.unregisterNetworkCallback(ob)
          }
        }.distinctUntilChanged()


    }
}