package com.example.app.data.model

import com.google.gson.annotations.SerializedName

data class BusResponse(
    @SerializedName("p")
    val busPrefix: Int?,
    @SerializedName("a")
    val isAccessible: Boolean?,
    @SerializedName("ta")
    val utcRequestHour: String?,
    @SerializedName("py")
    val lat: Double?,
    @SerializedName("px")
    val lng: Double?,
    val sv: Any?,
    val `is`: Any?,
)