package br.com.daniel.aikoandroidestagio.model

import java.io.Serializable

data class V(
    val p: Int,
    val a: Boolean,
    val ta: String,
    val py: Double,
    val px: Double,
) : Serializable {
    var position = 0
    var origem = ""
    var destino = ""
}
