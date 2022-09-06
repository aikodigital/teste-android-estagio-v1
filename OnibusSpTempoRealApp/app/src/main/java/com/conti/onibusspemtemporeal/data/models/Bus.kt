package com.conti.onibusspemtemporeal.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Bus(
    @SerializedName("p")
    val prefixBus: Int,
    @SerializedName("a")
    val acessibleBus: Boolean,
    @SerializedName("ta")
    val utcGetStatusHour: String,
    @SerializedName("py")
    val latBus: Double,
    @SerializedName("px")
    val longBus: Double
): Serializable
