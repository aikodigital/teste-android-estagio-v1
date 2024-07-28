package com.example.aikodigital.service.Response.maps.api_directions

data class MapsApiResponseList(
    val geocoded_waypoints: List<MapsApiResponse>,
    val routes: List<MapsRoutesResponse>,
    val status: String
)
