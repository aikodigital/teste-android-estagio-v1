package br.com.aiko.estagio.bussp.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class Empresas(
    val hr: String,
    val e: List<EmpresasAreaOcupcao>
)

@Serializable
data class EmpresasAreaOcupcao(
    val a: Int,
    val e: List<Empresa>
)

@Serializable
data class Empresa(
    val a: Int,
    val c: Int,
    val n: String
)
