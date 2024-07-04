package com.example.app.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class AllLinesResponse(
    @SerializedName("hr")
    val requestHour: String?,
    @SerializedName("l")
    val linesRelation: List<LineResponse?>?
)
