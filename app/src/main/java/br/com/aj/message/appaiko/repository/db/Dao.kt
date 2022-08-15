package br.com.aj.message.appaiko.repository.db

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.aj.message.appaiko.data.*


@Dao
interface DaoCorredor {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun   create(code: CorredorItem)

    @Update
    suspend fun  update(code: CorredorItem)

    @Delete
    suspend fun  delete(code: CorredorItem)

    @Query( "SELECT * FROM corredor_item")
    fun  getAll() : LiveData<List<CorredorItem>>

    @Query( "SELECT * FROM corredor_item WHERE id = :key")
    fun  get(key:Int) : LiveData<CorredorItem>


}
@Dao
interface DaoParada  {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun   create(code: Parada)

    @Update
    suspend fun  update(code: Parada)

    @Delete
    suspend fun  delete(code: Parada)

    @Query( "SELECT * FROM parada_item")
    fun  getAll() : LiveData<List<Parada>>

    @Query( "SELECT * FROM parada_item WHERE id = :key")
    fun  get(key:Int) : LiveData<Parada>
}


@Dao
interface DaoBus  {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun   create(code: PositionVehicles)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun   create(code: L)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun   create(code: V)

    @Update
    suspend fun  update(code: PositionVehicles)

    @Delete
    suspend fun  delete(code: PositionVehicles)

    @Query( "SELECT * FROM position_vehicles")
    fun  getAll() : LiveData<List<PositionVehicles>>

    @Query( "SELECT * FROM position_vehicles_l WHERE fid = :key")
   fun  getAllL(key:Int) : LiveData<List<L>>

    @Query( "SELECT * FROM position_vehicles_v WHERE fid = :key")
    fun  getAllV(key:Int) : LiveData<List<V>>

}