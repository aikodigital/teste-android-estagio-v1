package com.andreesperanca.deolhonobus.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BusStopPrediction(
    @SerializedName("hr")
    val hour: String,
    @SerializedName("p")
    val busStop: BusStopForecastModel
) : Parcelable
