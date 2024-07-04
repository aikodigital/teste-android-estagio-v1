package com.hilguener.spbusao.domain.model

import com.google.gson.annotations.SerializedName

data class PrevArrival(
    @SerializedName("hr")
    val hourReference: String = "",
    @SerializedName("p")
    val pointOfParade: PrevParade,
)
