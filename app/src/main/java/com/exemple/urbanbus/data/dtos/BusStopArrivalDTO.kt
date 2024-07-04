package com.exemple.urbanbus.data.dtos

import android.os.Parcelable
import com.exemple.urbanbus.data.models.BusStopLineArrival
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BusStopArrivalDTO(
    @SerializedName("hr") val hour: String,
    @SerializedName("p") val busStopData: BusStopData
) : Parcelable

@Parcelize
data class BusStopData(
    @SerializedName("cp") val code: Number,
    @SerializedName("np") val name: String,
    @SerializedName("py") val latitude: Double,
    @SerializedName("px") val longitude: Double,
    @SerializedName("l") val busLineArrival: List<BusStopLineArrival>
) : Parcelable