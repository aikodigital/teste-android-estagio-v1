package com.conti.onibusspemtemporeal.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResponseAllBus(
    @SerializedName("hr")
    val hourGet: String,
    @SerializedName("l")
    val lineRelation: List<BusRouteWithBus>
) : Serializable
