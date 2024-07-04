package com.exemple.urbanbus.data.dtos

import com.exemple.urbanbus.data.models.VehicleArrival

data class BusPostitionDTO(
    val hr: String,
    val vs: List<VehicleArrival>
)
