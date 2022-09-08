package com.andreesperanca.deolhonobus.data.local.daos

import androidx.room.*
import com.andreesperanca.deolhonobus.models.BusLine
import com.andreesperanca.deolhonobus.models.BusStop
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favoritesBusLine")
    fun getFavoritesBusLine() : Flow<List<BusLine>>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun favoriteBusLine (busLine: BusLine)

    @Query("DELETE FROM favoritesBusLine WHERE idCode = :idCode")
    fun deleteFavoriteBusLine(idCode: Int)


    @Query("SELECT * FROM favoritesBusLine WHERE idCode = :idCode")
    fun favoriteBusLineVerify(idCode: Int) : BusLine?



    //SEPARAÇÃO

    @Query("SELECT * FROM favoritesBusStop")
    fun getFavoritesBusStop() : Flow<List<BusStop>>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun favoriteBusStop (busStop: BusStop)

    @Query("DELETE FROM favoritesBusStop WHERE idCodeBusStop = :idCodeBusStop")
    fun deleteFavoriteBusStop(idCodeBusStop: Int)

    @Query("SELECT * FROM favoritesBusStop WHERE idCodeBusStop = :idCodeBusStop")
    fun favoriteBusStopVerify(idCodeBusStop: Int) : BusStop?

}
