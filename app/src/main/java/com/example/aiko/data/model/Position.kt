package com.example.aiko.data.model
import androidx.annotation.Keep
import kotlinx.serialization.Contextual

import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


@Keep
@Serializable
data class Position(
    @SerialName("hr")
    val hr: String,
    @SerialName("l")
    val l: List<L>
)

@Keep
@Serializable
data class L(
    @SerialName("c")
    val c: String,
    @SerialName("cl")
    val cl: Int,
    @SerialName("lt0")
    val lt0: String,
    @SerialName("lt1")
    val lt1: String,
    @SerialName("qv")
    val qv: Int,
    @SerialName("sl")
    val sl: Int,
    @SerialName("vs")
    val vs: List<V>
)

@Keep
@Serializable
data class V(
    @SerialName("a")
    val a: Boolean,
    @SerialName("is")
    val isX: @Contextual Any,
    @SerialName("p")
    val p: Int,
    @SerialName("px")
    val px: Double,
    @SerialName("py")
    val py: Double,
    @SerialName("sv")
    val sv: @Contextual Any,
    @SerialName("ta")
    val ta: String
)