package br.com.daniel.aikoandroidestagio.model

import java.io.Serializable

data class P(
    val cp: Int,
    val np: String,
    val px: Double,
    val py: Double,
    val l: List<LX>,
) : Serializable