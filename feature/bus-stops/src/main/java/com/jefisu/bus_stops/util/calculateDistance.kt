package com.jefisu.bus_stops.util

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

fun calculateDistance(startLat: Double, startLon: Double, endLat: Double, endLon: Double): Double {
    val earthRadius = 6371.0 // Radius of the Earth in kilometers

    val latDistance = Math.toRadians(endLat - startLat)
    val lonDistance = Math.toRadians(endLon - startLon)

    val haversineFormula = sin(latDistance / 2).pow(2.0) +
            cos(Math.toRadians(startLat)) * cos(Math.toRadians(endLat)) *
            sin(lonDistance / 2).pow(2.0)

    val angularDistance = 2 * atan2(sqrt(haversineFormula), sqrt(1 - haversineFormula))

    return earthRadius * angularDistance // returns the distance in kilometers
}