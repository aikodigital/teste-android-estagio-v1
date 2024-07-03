package com.hilguener.spbusao.domain.model

import com.google.gson.annotations.SerializedName

data class PrevParade(
    @SerializedName("cp")
    val codeOfParade: Int = 0,
    @SerializedName("np")
    val nameOfParade: String = "",
    @SerializedName("py")
    val latitudeOfVehicle: Double = 0.0,
    @SerializedName("px")
    val longitudeOfVehicle: Double = 0.0,
    @SerializedName("l")
    val lines: List<PrevLine> = emptyList(),
)
