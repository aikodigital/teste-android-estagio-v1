package com.hilguener.spbusao.domain.model

import com.google.gson.annotations.SerializedName

data class PosVehiclesByLines(
    @SerializedName("vs")
    val listOfVehicles: List<Vehicles> = emptyList(),
)
