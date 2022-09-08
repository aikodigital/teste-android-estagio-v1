package com.andreesperanca.deolhonobus.models

import com.google.gson.annotations.SerializedName

data class BusLinePosition(
    @SerializedName("hr")
    val hour: String,
    @SerializedName("vs")
    val locations: List<Localization>
)
