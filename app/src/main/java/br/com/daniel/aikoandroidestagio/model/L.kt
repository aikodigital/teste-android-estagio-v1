package br.com.daniel.aikoandroidestagio.model

import java.io.Serializable

data class L(
    val c: String,
    val cl: Int,
    val sl: Int,
    val lt0: String,
    val lt1: String,
    val qv: Int,
    val vs: List<V>
) : Serializable