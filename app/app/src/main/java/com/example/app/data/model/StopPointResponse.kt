package com.example.app.data.model

import com.google.gson.annotations.SerializedName

data class StopPointResponse(
    @SerializedName("cp")
    val stopPointCode: Int?,
    @SerializedName("np")
    val stopPointName: String?,
    @SerializedName("ed")
    val stopPointAddress: String?,
    @SerializedName("py")
    val lat: Double?,
    @SerializedName("px")
    val lng: Double?
)