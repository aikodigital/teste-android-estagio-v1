package br.com.aj.message.appaiko.repository.db

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.aj.message.appaiko.data.*


@Dao
interface DaoCorredor {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun   create(code: CorredorItem):Long

    @Update
    suspend fun  update(code: CorredorItem)

    @Delete
    suspend fun  delete(code: CorredorItem)

    @Query( "SELECT * FROM corredor_item")
    fun  getAll() : LiveData<List<CorredorItem>>

    @Query( "SELECT * FROM corredor_item WHERE cc = :key")
    fun  get(key:Long) : LiveData<CorredorItem>


}
@Dao
interface DaoParada  {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun   create(code: Parada) :Long

    @Update
    suspend fun  update(code: Parada)

    @Delete
    suspend fun  delete(code: Parada)

    @Query( "SELECT * from parada_item")
    fun  getAll() : List<Parada>

    @Query( "SELECT * FROM parada_item WHERE cp = :key")
    fun  get(key:Long) : Parada
}


@Dao
interface DaoBus  {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun   create(code: PositionVehicles) :Long
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun   create(code: L) :Long
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun   create(code: V)

    @Update
    suspend fun  update(code: PositionVehicles)
    @Update
    suspend fun  update(code: L)
    @Update
    suspend fun  update(code: V)
    @Delete
    suspend fun  delete(code: PositionVehicles)

    @Query( "SELECT * FROM position_vehicles")
    fun  getAll() : LiveData<List<PositionVehicles>>

    @Query( "SELECT * FROM position_vehicles_l WHERE fid = :key")
   fun  getAllL(key:Int) : List<L>

    @Query( "SELECT * FROM position_vehicles_v WHERE fid = :key")
    fun  getAllV(key: Long) : List<V>

}