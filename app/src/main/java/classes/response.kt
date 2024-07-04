package classes

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val cep: String,
    val logradouro: String,
    val complemento: String,
    val localidade: String,
    val uf: String
)

@Serializable
data class Parada(
   val cp: Int,
   val np: String,
   val ed: String,
   val py: Double,
   val px: Double
)

@Serializable
data class Linha(
    val cl: Int,
    val lc: Boolean,
    val lt: String,
    val tl: Int,
    val sl: Int,
    val tp: String,
    val ts: String
)

@Serializable
data class Veiculo(
   val p: Int,
   val a: Boolean,
   val ta: String,
   val py: Double,
   val px: Double
)

@Serializable
data class Posicao(
    val hr: String,
    val vs: List<Veiculo>
)

@Serializable
data class VeiculoPrevisao(
    val p: Int,
    val t: String,
    val a: Boolean,
    val ta: String,
    val py: Double,
    val px: Double
)

@Serializable
data class LinhaPrevisao(
    val c: String,
    val cl: Int,
    val sl: Int,
    val lt0: String,
    val lt1: String,
    val qv: Int,
    val vs: List<VeiculoPrevisao>
)

@Serializable
data class ParadaPrevisao(
    val cp: Int,
    val np: String,
    val py: Double,
    val px: Double,
    val l: List<LinhaPrevisao>
)

@Serializable
data class Previsao(
    val hr: String,
    val p: ParadaPrevisao
)








