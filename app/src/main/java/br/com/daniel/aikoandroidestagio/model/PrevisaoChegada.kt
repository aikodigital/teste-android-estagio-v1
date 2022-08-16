package br.com.daniel.aikoandroidestagio.model

import java.io.Serializable

data class PrevisaoChegada(
    val hr: String,
    val p: P?
) : Serializable