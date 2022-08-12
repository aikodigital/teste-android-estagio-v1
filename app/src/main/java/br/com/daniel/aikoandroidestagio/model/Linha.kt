package br.com.daniel.aikoandroidestagio.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Linha(
    val identificador: Int,
    val circular: Boolean,
    val letreiroNumerico: String,
    val modo: Int,
    val sentido: Int,
    val letreiroDescritivo1: String,
    val letreiroDescritivo2: String
) : Parcelable