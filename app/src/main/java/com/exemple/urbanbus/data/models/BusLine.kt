package com.exemple.urbanbus.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BusLine(
    @SerializedName("cl") val code: Int,
    @SerializedName("lc") val circular: Boolean,
    @SerializedName("lt") val sign: String,
    @SerializedName("tl") val signId: Int,
    @SerializedName("sl") val direction: Int,
    @SerializedName("tp") val mainTerminal: String,
    @SerializedName("ts") val secondaryTerminal: String
) : Parcelable