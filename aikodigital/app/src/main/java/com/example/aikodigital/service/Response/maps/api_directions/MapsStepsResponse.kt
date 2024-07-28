package com.example.aikodigital.service.Response.maps.api_directions

data class MapsStepsResponse(
    val distance: MapsDistanceDurationResponse,
    val duration: MapsDistanceDurationResponse,
    val end_location: MapsStartEndLocationResponse,
    val polyline: MapsPolylineResponse,
    val start_location: MapsStartEndLocationResponse,
    val travel_mode: String

)
