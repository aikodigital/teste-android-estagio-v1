package com.conti.onibusspemtemporeal.domain.roomRepository

import com.conti.onibusspemtemporeal.data.models.BusRoute
import com.conti.onibusspemtemporeal.data.room.BusDataSourceImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val busDataSourceImpl: BusDataSourceImpl
) {

    /** Inserts */
    suspend fun favoriteBusRoute(busRoute: BusRoute) = busDataSourceImpl.insertBusRoute(busRoute)

    /** Delete */
    suspend fun deleteBusRoute(busRoute: BusRoute) = busDataSourceImpl.deleteBusRoute(busRoute)

    /** Gets */
    fun getFavoritesBusRoutes(): Flow<List<BusRoute>> = busDataSourceImpl.getFavoritesBusRoutes()

}