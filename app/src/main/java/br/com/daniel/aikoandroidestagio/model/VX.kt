package br.com.daniel.aikoandroidestagio.model

import java.io.Serializable

data class VX(
    val p: String,
    val t: String,
    val a: Boolean,
    val ta: String,
    val py: Double,
    val px: Double
) : Serializable