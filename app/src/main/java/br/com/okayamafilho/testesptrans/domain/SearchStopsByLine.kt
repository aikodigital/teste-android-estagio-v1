package br.com.okayamafilho.testesptrans.domain

data class SearchStopsByLine(
    val cp: Int,
    val np: String,
    val ed: String,
    val px: Double,
    val py: Double
)