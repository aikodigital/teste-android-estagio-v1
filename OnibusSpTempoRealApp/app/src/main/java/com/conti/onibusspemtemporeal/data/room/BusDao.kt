package com.conti.onibusspemtemporeal.data.room

import androidx.room.*
import com.conti.onibusspemtemporeal.data.models.BusRoute
import kotlinx.coroutines.flow.Flow

@Dao
interface BusDao {

    /** Inserts */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBusRoute(busRoute: BusRoute)

    /** Delete */
    @Delete
    suspend fun deleteBusRoute(busRoute: BusRoute)

    /** Gets */
    @Query("SELECT * FROM bus_route_favorite")
    fun getFavoritesBusRoutes(): Flow<List<BusRoute>>

}