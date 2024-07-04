package com.example.app.domain.model

import com.google.gson.annotations.SerializedName

data class BusWithTime(
    @SerializedName("p")
    val busPrefix: Int,
    @SerializedName("t")
    val arrivalForecast: String,
    @SerializedName("a")
    val isAccessible: Boolean,
    @SerializedName("ta")
    val utcRequestHour: String,
    @SerializedName("py")
    val lat: Double,
    @SerializedName("px")
    val lng: Double
)