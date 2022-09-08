package com.conti.onibusspemtemporeal.data.room

import com.conti.onibusspemtemporeal.data.models.BusRoute
import kotlinx.coroutines.flow.Flow

interface BusDataSourceInterface {

    /** Inserts */
    suspend fun insertBusRoute(busRoute: BusRoute)

    /** Delete */
    suspend fun deleteBusRoute(busRoute: BusRoute)

    /** Gets */
    fun getFavoritesBusRoutes(): Flow<List<BusRoute>>
}