package com.example.app.domain.model

import com.google.gson.annotations.SerializedName

data class AllLines(
    @SerializedName("hr")
    val requestHour: String,
    @SerializedName("l")
    val linesRelation: List<Line?>
)
