package br.com.aj.message.appaiko.repository.db

import androidx.lifecycle.LiveData
import br.com.aj.message.appaiko.data.CorredorItem
import br.com.aj.message.appaiko.data.Parada
import br.com.aj.message.appaiko.data.PositionVehicles
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseRepository(private val dao: MyDatabase?) {


    suspend fun createCorredor(item: CorredorItem) {
        withContext(Dispatchers.IO) { dao?.daoCorredor?.create(item) }
    }

    suspend fun createParada(item: Parada) {
        withContext(Dispatchers.IO) { dao?.daoParada?.create(item) }
    }

    fun getAllCorredor(): LiveData<List<CorredorItem>>? {
        return dao?.daoCorredor?.getAll()
    }

    fun getAllParada(): LiveData<List<Parada>>? {
        return dao?.daoParada?.getAll()
    }

    suspend fun getAllBus(): List<PositionVehicles>? {
        return withContext(Dispatchers.IO) {
            val position = dao?.daoBus?.getAll()
            val p = position?.value
            val positionVehicles = p?.map { itp ->
                val tmpl = dao?.daoBus?.getAllL(itp.id?.toInt()!!)?.value?.map { itl ->
                    val tmpv = dao.daoBus.getAllV(itl.id?.toInt()!!).value

                    if (tmpv != null) {
                        itl.vs?.addAll(tmpv)
                    }
                    itl
                }
                if (tmpl != null) {
                    itp.l?.addAll(tmpl)
                }

                itp
            }
            return@withContext positionVehicles
        }
    }

}