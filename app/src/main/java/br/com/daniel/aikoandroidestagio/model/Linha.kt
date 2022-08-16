package br.com.daniel.aikoandroidestagio.model

import java.io.Serializable

data class Linha(
    val identificador: Int,
    val circular: Boolean,
    val letreiroNumerico: String,
    val modo: Int,
    val sentido: Int,
    val letreiroDescritivo1: String,
    val letreiroDescritivo2: String
) : Serializable