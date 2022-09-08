package com.andreesperanca.deolhonobus.repositories

import android.app.Application
import com.andreesperanca.deolhonobus.R
import com.andreesperanca.deolhonobus.data.local.daos.FavoriteDao
import com.andreesperanca.deolhonobus.data.remote.RetrofitService
import com.andreesperanca.deolhonobus.models.BusLine
import com.andreesperanca.deolhonobus.models.BusStop
import com.andreesperanca.deolhonobus.models.Place
import com.andreesperanca.deolhonobus.util.Resource
import com.andreesperanca.deolhonobus.util.apiCall
import com.andreesperanca.deolhonobus.util.dateStringFormatter
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await
import java.util.*

class BusDetailsRepository(
    private val service: RetrofitService,
    private val favoriteDao: FavoriteDao) {

    suspend fun getBusStopWithBusLineCode(codigoLinha: String): Resource<List<BusStop>> {
        return withContext(Dispatchers.IO) {
            apiCall {
                val fetchResult = service.getBusStopWithBusLineCode(codigoLinha).await()
                Resource.Success(fetchResult)
            }
        }
    }

    suspend fun getBusPositionWithBusLineCode(busLineCode: String): Resource<List<Place>> {
        return withContext(Dispatchers.IO) {
            apiCall {
                val resultFetch = service.getPositionBusLineWithBusLineCode(busLineCode).await()
                val resultMap = mutableListOf<Place>()
                resultFetch.locations.let { it.forEach {
                    val newPlace =
                        Place(
                            title = "Prefixo Veículo: ${it.prefixVehicle}", lng = LatLng(it.py,it.px))
                    resultMap.add(newPlace)
                } }
                Resource.Success(resultMap)
            }
        }
    }

    suspend fun favoriteBusLine(busLine: BusLine) {
        favoriteDao.favoriteBusLine(busLine)
    }

    fun deleteFavoriteBusLine(key: Int) {
        favoriteDao.deleteFavoriteBusLine(key)
    }

    fun favoriteVerify(key: Int) : BusLine? = favoriteDao.favoriteBusLineVerify(key)
}