package br.com.aiko.estagio.bussp.data.remote.response

data class PrevisaoChegadaLinha(
    val hr: String,
    val ps: List<PontoParadaLinha>
)

data class PontoParadaLinha(
    val cp: Int,
    val np: String,
    val py: Double,
    val px: Double,
    val vs: List<VeiculoLocalizado>
)

