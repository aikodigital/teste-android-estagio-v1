package com.example.aikodigital.service.Response.maps.api_directions

data class MapsApiResponse(
    val geocoder_status: String,
    val place_id: String,
    val types: List<String>,

)
