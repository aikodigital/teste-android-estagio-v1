package br.com.okayamafilho.testesptrans.domain

data class StopForecastLine(
    val cp: Int,
    val l: List<StopForecastLineLocation>,
    val np: String,
    val px: Double,
    val py: Double
)