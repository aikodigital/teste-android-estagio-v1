package com.hilguener.spbusao.domain.model

import com.google.gson.annotations.SerializedName

data class PosLines(
    @SerializedName("c")
    val busSing: String = "",
    @SerializedName("cl")
    val codeLine: Int = 0,
    // 1 significa de Terminal Principal para Terminal Secundário e 2 de Terminal Secundário para Terminal Principal
    @SerializedName("sl")
    val directionOperation: Int = 0,
    @SerializedName("lt0")
    val destinationSign: String = "",
    @SerializedName("lt1")
    val originSign: String = "",
    @SerializedName("qv")
    val quantityOfVehicles: Int = 0,
    @SerializedName("vs")
    val listOfVehicles: List<Vehicles> = emptyList(),
)
