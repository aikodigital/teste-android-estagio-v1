package br.com.samuel.testeaiko.core.domain.model

import java.io.Serializable

data class BusLine(
    val cl: Int,
    val lc: Boolean,
    val lt: String,
    val sl: Int,
    val tl: Int,
    val tp: String,
    val ts: String
) : Serializable {
    fun busSign() = "$lt-$tl"

    fun routeName(): String {
        return when (sl) {
            1 -> "$tp / $ts"
            2 -> "$ts / $tp"
            else -> ""
        }
    }

    fun destination(): String {
        return when (sl) {
            1 -> tp
            2 -> ts
            else -> ""
        }
    }

}
