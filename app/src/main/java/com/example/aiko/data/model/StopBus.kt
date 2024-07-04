package com.example.aiko.data.model
import androidx.annotation.Keep

import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


class StopBus : ArrayList<StopBusItem>()

@Keep
@Serializable
data class StopBusItem(
    @SerialName("cp")
    val cp: Int,
    @SerialName("ed")
    val ed: String,
    @SerialName("np")
    val np: String,
    @SerialName("px")
    val px: Double,
    @SerialName("py")
    val py: Double
)