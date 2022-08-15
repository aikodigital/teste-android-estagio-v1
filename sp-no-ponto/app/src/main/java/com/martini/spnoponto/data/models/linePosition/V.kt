package com.martini.spnoponto.data.models.linePosition

import com.martini.spnoponto.domain.entities.linePosition.LinePosition

//Classe gerada com o plugin "Kotlin data class file from JSON"
data class V(
    val a: Boolean,
    val p: String,
    val px: Double,
    val py: Double,
    val ta: String
)

fun V.toLinePosition(): LinePosition {
    return LinePosition(
        a,
        p,
        px,
        py,
        ta
    )
}