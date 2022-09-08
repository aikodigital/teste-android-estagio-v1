package com.andreesperanca.deolhonobus.repositories

import com.andreesperanca.deolhonobus.data.local.daos.FavoriteDao
import com.andreesperanca.deolhonobus.models.BusLine
import com.andreesperanca.deolhonobus.models.BusStop
import kotlinx.coroutines.flow.Flow

class FavoriteRepository (private val favoriteDao: FavoriteDao ) {

    val allFavoritesBusLines : Flow<List<BusLine>> = favoriteDao.getFavoritesBusLine()

    val allFavoritesBusStops : Flow<List<BusStop>> = favoriteDao.getFavoritesBusStop()

}