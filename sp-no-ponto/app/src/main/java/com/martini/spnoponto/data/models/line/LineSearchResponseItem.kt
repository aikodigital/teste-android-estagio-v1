package com.martini.spnoponto.data.models.line

//Classe gerada com o plugin "Kotlin data class file from JSON"
import com.martini.spnoponto.domain.entities.lineSearch.Linha

data class LineSearchResponseItem(
    val cl: Int,
    val lc: Boolean,
    val lt: String,
    val sl: Int,
    val tl: Int,
    val tp: String,
    val ts: String
)

fun LineSearchResponseItem.toLinha(): Linha {
    return Linha(
        cl,
        lc,
        lt,
        sl,
        tl,
        tp,
        ts
    )
}