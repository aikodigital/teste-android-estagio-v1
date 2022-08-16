package br.com.daniel.aikoandroidestagio.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Parada(
    @SerializedName("cp")
    val id: Int,
    @SerializedName("np")
    val nome: String,
    @SerializedName("ed")
    val enderecoParada: String,
    @SerializedName("py")
    val latitude: Double,
    @SerializedName("px")
    val longitude: Double
) : Serializable

