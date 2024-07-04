package com.example.aiko_desafio.objetos

// Objeto para receber o JSON das posições dos veiculos da classe Mapa_veiculos
data class PosicaoVeiculos(
    val hr: String,
    val l: List<linha_veiculo>
)

data class linha_veiculo(
    val c: String,
    val cl: Int,
    val sl: Int,
    val lt0: String,
    val lt1: String,
    val qv: Int,
    val vs: List<veiculo_veiculo>
)

data class veiculo_veiculo(
    val p: Int,
    val a: Boolean,
    val ta: String,
    val py: Double,
    val px: Double
)