package com.hilguener.spbusao.domain.model

import com.google.gson.annotations.SerializedName

data class Parades(
    @SerializedName("cp")
    val codeOfParade: Int = 0,
    @SerializedName("np")
    val nameOfParade: String = "",
    @SerializedName("ed")
    val addressOfParade: String = "",
    @SerializedName("py")
    val latitude: Double = 0.0,
    @SerializedName("px")
    val longitude: Double = 0.0,
)
