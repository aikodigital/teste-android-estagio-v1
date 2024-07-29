package com.example.aikodigital.service.responses.maps.api_directions

data class MapsRoutesResponse(
    val bounds: MapsBoundsResponse,
    val copyrights: String,
    val legs: List<MapsLegsResponse>,
    val overview_polyline: MapsPolylineResponse,
    val summary: String,

)

data class MapsBoundsResponse(
    val northeast: MapsStartEndLocationResponse,
    val southwest: MapsStartEndLocationResponse
)


data class MapsPolylineResponse(
    val points: String
)