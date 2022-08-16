package br.com.aj.message.appaiko.repository.db

import android.util.Log
import androidx.lifecycle.LiveData
import br.com.aj.message.appaiko.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseRepository(private val dao: MyDatabase?) {

    suspend fun createCorredor(item: CorredorItem) {
        withContext(Dispatchers.IO) { dao?.daoCorredor?.create(item) }
    }

    suspend fun createParada(item: Parada) {
        withContext(Dispatchers.IO) { dao?.daoParada?.create(item) }
    }

    suspend fun createV(item: V) {
        withContext(Dispatchers.IO) { dao?.daoBus?.create(item) }
    }

    suspend fun createVehicles(item: PositionVehicles) {
        withContext(Dispatchers.IO) { dao?.daoBus?.create(item) }
    }

    suspend fun createL(item: L)  {
        withContext(Dispatchers.IO) { dao?.daoBus?.create(item) }
    }

    fun getAllCorredor(): LiveData<List<CorredorItem>>? {
        return dao?.daoCorredor?.getAll()
    }

   suspend fun getAllParada(): List<Parada>? {
        return withContext(Dispatchers.IO) {  dao?.daoParada?.getAll() }
    }

    suspend fun getAllBus(): PositionVehicles? {
        return withContext(Dispatchers.IO) {

                val ls =  arrayListOf<L>()
                    dao?.daoBus?.getAllL(1)?.forEach {
                        val vs = arrayListOf<V>()
                         vs.addAll(dao.daoBus.getAllV(it.cl))
                        it.vs = vs
                      ls.add(it)
                    }
            return@withContext PositionVehicles(1,"",ls)
        }
    }

}