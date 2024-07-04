package com.jefisu.bus_stops.util

import com.google.android.gms.maps.model.LatLng
import com.jefisu.domain.model.Line

fun findCentralVehiclePosition(lines: List<Line>): LatLng? {
    val allVehicles = lines.flatMap { it.vehicles }
    if (allVehicles.isEmpty()) return null

    val avgLatitude = allVehicles.map { it.latitude }.average()
    val avgLongitude = allVehicles.map { it.longitude }.average()

    val center = Pair(avgLatitude, avgLongitude)

    val centralVehicle = allVehicles.minByOrNull {
        calculateDistance(it.latitude, it.longitude, center.first, center.second)
    }

    return centralVehicle?.let { LatLng(it.latitude, it.longitude) }
}