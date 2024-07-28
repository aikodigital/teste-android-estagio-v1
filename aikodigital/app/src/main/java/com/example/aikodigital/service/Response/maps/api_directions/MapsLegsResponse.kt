package com.example.aikodigital.service.Response.maps.api_directions

data class MapsLegsResponse(
    val distance: MapsDistanceDurationResponse,
    val duration: MapsDistanceDurationResponse,
    val end_address: String,
    val end_location: MapsStartEndLocationResponse,
    val start_address: String,
    val start_location: MapsStartEndLocationResponse,
    val steps: List<MapsStepsResponse>,
)
