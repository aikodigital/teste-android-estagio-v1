package com.conti.onibusspemtemporeal.data.room

import com.conti.onibusspemtemporeal.data.models.BusRoute
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BusDataSourceImpl @Inject constructor(
    private val busDao: BusDao
) : BusDataSourceInterface {

    /** Inserts */
    override suspend fun insertBusRoute(busRoute: BusRoute) = busDao.insertBusRoute(busRoute)

    /** Delete */
    override suspend fun deleteBusRoute(busRoute: BusRoute) = busDao.deleteBusRoute(busRoute)

    /** Gets */
    override fun getFavoritesBusRoutes(): Flow<List<BusRoute>> = busDao.getFavoritesBusRoutes()
}