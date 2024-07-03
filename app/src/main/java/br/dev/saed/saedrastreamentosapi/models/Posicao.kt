package br.dev.saed.saedrastreamentosapi.models

data class Posicao(
    val hr: String,
    val l: List<LinhasPosicao>

) {
    override fun toString(): String {
        return "Hr(hr='$hr', l=$l)"
    }
}
