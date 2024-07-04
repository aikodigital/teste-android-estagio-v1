package com.jefisu.bus_stops.util

import kotlin.math.ln

fun calculateZoomLevel(numVehicles: Int): Float {
    val maxZoom = 13f
    val minZoom = 8f
    val zoomRange = maxZoom - minZoom

    // Adjust this factor to control how quickly the zoom level decreases
    val sensitivityFactor = 0.5f

    val adjustedZoom = (sensitivityFactor * ln(numVehicles.toDouble() + 1)).toFloat()
    val zoomLevel = maxZoom - (adjustedZoom / zoomRange)

    return zoomLevel.coerceIn(minZoom, maxZoom)
}