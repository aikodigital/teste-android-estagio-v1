package com.aiko.bus.repositories

import com.aiko.bus.models.BusLane
import com.aiko.bus.sptrans.SPTransClient

class BusLaneRepository {

    suspend fun getBusLanes(): List<BusLane> {
        return SPTransClient.getBusLanes().map {
            BusLane(
                id = it.id,
                name = it.name
            )
        }
    }

}