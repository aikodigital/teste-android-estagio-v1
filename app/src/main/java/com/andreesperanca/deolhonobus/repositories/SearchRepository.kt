package com.andreesperanca.deolhonobus.repositories

import com.andreesperanca.deolhonobus.data.local.daos.FavoriteDao
import com.andreesperanca.deolhonobus.data.remote.RetrofitService
import com.andreesperanca.deolhonobus.models.BusLine
import com.andreesperanca.deolhonobus.models.BusStop
import com.andreesperanca.deolhonobus.util.Resource
import com.andreesperanca.deolhonobus.util.apiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await

class SearchRepository(
    private val service: RetrofitService,
    private val favoriteDao: FavoriteDao
    ) {

    suspend fun getAuthInApi(): Resource<String>{
        return withContext(Dispatchers.IO) {
            apiCall {
                val auth = service.getAuthInAPI().await()
                Resource.Success(auth)
            }
        }
    }


    suspend fun getBusLines(searchTerms: String): Resource<List<BusLine>> {
        return withContext(Dispatchers.IO) {
            apiCall {
                val listBusLines = service.getBusLinesWithNameOrNumber(searchTerms).await()
                Resource.Success(listBusLines)
            }
        }
    }

    suspend fun getBusStopWithAddressOrName(searchTerms: String) : Resource<List<BusStop>> {
        return withContext(Dispatchers.IO) {
            apiCall {
                val listBusStop = service.getBusStopWithAddressOrName(searchTerms).await()
                Resource.Success(listBusStop)
            }
        }
    }

    suspend fun getBusStopWithHallCode(hallCode: String) : Resource<List<BusStop>> {
        return withContext(Dispatchers.IO) {
            apiCall {
                val listBusStop = service.getBusStopWithHallCode(hallCode).await()
                Resource.Success(listBusStop)
            }
        }
    }

    suspend fun getBusStopWithBusLineCode(codigoLinha: String): Resource<List<BusStop>> {
        return withContext(Dispatchers.IO) {
            apiCall {
                val fetchResult = service.getBusStopWithBusLineCode(codigoLinha).await()
                Resource.Success(fetchResult)
            }
        }
    }
}