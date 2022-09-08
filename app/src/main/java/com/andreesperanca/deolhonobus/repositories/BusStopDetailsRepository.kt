package com.andreesperanca.deolhonobus.repositories

import com.andreesperanca.deolhonobus.data.local.daos.FavoriteDao
import com.andreesperanca.deolhonobus.data.remote.RetrofitService
import com.andreesperanca.deolhonobus.models.BusLine
import com.andreesperanca.deolhonobus.models.BusStop
import com.andreesperanca.deolhonobus.models.ForecastVehicleView
import com.andreesperanca.deolhonobus.util.Resource
import com.andreesperanca.deolhonobus.util.apiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await

class BusStopDetailsRepository(
    private val service: RetrofitService,
    private val favoriteDao: FavoriteDao) {


    suspend fun getForecastWithBusStopCode (busStopCode: String) : Resource<List<ForecastVehicleView>> {
        return withContext(Dispatchers.IO) {
            apiCall {
                val resultFetch = service.getForecastWithBusStopCode(busStopCode).await()
                val resultMap = mutableListOf<ForecastVehicleView>()
                resultFetch.busStop.listOfLinesFound.map {
                    val forecastView =
                        ForecastVehicleView(
                        sign = it.sign,
                        origin = it.origin,
                        lineWay = it.lineWay,
                        destination = it.destination,
                        vehicleList = it.vehicleList)
                    resultMap.add(forecastView)
                }
                Resource.Success(resultMap)
            }
        }
    }


    suspend fun favoriteBusStop(busStop: BusStop) {
        favoriteDao.favoriteBusStop(busStop)
    }

    fun deleteFavoriteBusLine(key: Int) {
        favoriteDao.deleteFavoriteBusStop(key)
    }

    fun favoriteBusStopVerify(key: Int) : BusStop? = favoriteDao.favoriteBusStopVerify(key)
}