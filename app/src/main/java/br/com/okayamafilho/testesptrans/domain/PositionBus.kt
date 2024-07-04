package br.com.okayamafilho.testesptrans.domain

data class PositionBus(
    val hr: String,
    val vs: List<PositionBusInformation>
)