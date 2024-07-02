package br.com.samuel.testeaiko.core.domain.enums

enum class BusLineDirections(val value: Int) {
    // Terminal Principal para Terminal Secundário
    PRINCIPAL_TO_SECONDARY(1),

    // Para Terminal Secundário para Terminal Principal
    SECONDARY_TO_PRINCIPAL(2)
}