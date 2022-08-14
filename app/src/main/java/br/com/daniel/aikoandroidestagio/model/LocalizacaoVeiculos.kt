package br.com.daniel.aikoandroidestagio.model

import java.io.Serializable

data class LocalizacaoVeiculos(
    val hr: String,
    val l: List<L>
) : Serializable {
    fun isEmpty(): Boolean {
        return l.isEmpty()
    }
}