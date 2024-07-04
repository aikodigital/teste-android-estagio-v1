package com.hilguener.spbusao.domain.model

import com.google.gson.annotations.SerializedName

data class PrevLine(
    @SerializedName("c")
    val signOfLine: String = "",
    @SerializedName("cl")
    val codeOfLine: Int = 0,
    // Sentido de operação onde 1 significa de
    // Terminal Principal para Terminal Secundário e 2 de Terminal Secundário para Terminal Principal
    @SerializedName("sl")
    val directionOfWorks: Int = 0,
    @SerializedName("lt0")
    val signOfDestinyOfLine: String = "",
    @SerializedName("lt1")
    val singOfOriginOfLine: String = "",
    @SerializedName("qv")
    val quantityOfVehicles: Int = 0,
    @SerializedName("vs")
    val vehicles: List<PrevVehicle> = emptyList(),
)
