package com.aiko.bus.repositories

import com.aiko.bus.models.Stop
import com.aiko.bus.sptrans.SPTransClient
import com.aiko.bus.sptrans.response.StopResponse

class StopRepository {

    suspend fun getStopsLine(lineId: Int): List<Stop> {
        return SPTransClient.getStopByLine(lineId).map {
            parse(it)
        }
    }

    suspend fun getStopsByBusLane(busLaneId: Int): List<Stop> {
        return SPTransClient.getStopByBusLane(busLaneId).map {
            parse(it)
        }
    }

    private fun parse(stop: StopResponse): Stop {
        return Stop(
            id = stop.id,
            name = stop.name,
            latitude = stop.latitude,
            longitude = stop.longitude
        )
    }
}
