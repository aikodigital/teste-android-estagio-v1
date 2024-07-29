package com.aiko.bus.repositories

import com.aiko.bus.models.Vehicle
import com.aiko.bus.sptrans.SPTransClient


class VehicleRepository {

    suspend fun getPositionsByLine(lineId: Int): List<Vehicle> {
        return SPTransClient.getVehiclePositionsByLane(lineId).vehicles.map {
            Vehicle(
                id = it.id,
                latitude = it.latitude,
                longitude = it.longitude,
                isAccessible = it.isAccessible
            )
        }
    }

}