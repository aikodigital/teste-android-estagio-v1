package com.aiko.domain.model

import com.squareup.moshi.Json
import java.util.UUID

data class StopModel(
    val uniqueID: String = UUID.randomUUID().toString(),
    @Json(name = "cp") val codigoParada: Int?,
    @Json(name = "np") val nomeParada: String?,
    @Json(name = "ed") val enderecoParada: String?,
    @Json(name = "py") val latitude: Double?,
    @Json(name = "px") val longitude: Double?
)

data class ForecastModel(
    val uniqueID: String = UUID.randomUUID().toString(),
    @Json(name = "hr") val horarioReferencia: String?,
    @Json(name = "p") val pontoParada: StopPointModel?
)

data class StopPointModel(
    @Json(name = "cp") val codigoParada: Int?,
    @Json(name = "np") val nomeParada: String?,
    @Json(name = "py") val latitude: Double?,
    @Json(name = "px") val longitude: Double?,
    @Json(name = "l") val linhas: List<ForecastLineModel>?
)

data class LineModel(
    @Json(name = "cl") val codigoLinha: Int?,
    @Json(name = "lc") val circular: Boolean?,
    @Json(name = "lt") val letreiroNumerico: String?,
    @Json(name = "sl") val sentido: Int?,
    @Json(name = "tl") val tipoLinha: Int?,
    @Json(name = "tp") val terminalPrincipal: String?,
    @Json(name = "ts") val terminalSecundario: String?
)

data class ForecastLineModel(
    @Json(name = "c") val letreiroCompleto: String?,
    @Json(name = "cl") val codigoLinha: Int?,
    @Json(name = "sl") val sentidoLinha: Int?,
    @Json(name = "lt0") val destino: String?,
    @Json(name = "lt1") val origem: String?,
    @Json(name = "qv") val quantidadeVeiculos: Int?,
    @Json(name = "vs") val veiculos: List<ForecastVehicleModel>?
)

data class ForecastVehicleModel(
    @Json(name = "p") val prefixoVeiculo: String?,
    @Json(name = "t") val horarioPrevisto: String?,
    @Json(name = "a") val acessivel: Boolean?,
    @Json(name = "ta") val horarioCaptura: String?,
    @Json(name = "py") val latitude: Double?,
    @Json(name = "px") val longitude: Double?
)

data class VehiclePositionModel(
    @Json(name = "hr") val horarioReferencia: String?,
    @Json(name = "l") val linhasVeiculos: List<VehicleLineModel>?
)

data class VehicleLineModel(
    @Json(name = "c") val letreiroCompleto: String?,
    @Json(name = "cl") val codigoLinha: Int?,
    @Json(name = "sl") val sentidoLinha: Int?,
    @Json(name = "lt0") val destino: String?,
    @Json(name = "lt1") val origem: String?,
    @Json(name = "qv") val quantidadeVeiculos: Int?,
    @Json(name = "vs") val vehicleModels: List<VehicleModel>?
)

data class VehicleModel(
    @Json(name = "p") val prefixoVeiculo: Int?,
    @Json(name = "a") val acessivel: Boolean?,
    @Json(name = "ta") val horarioCaptura: String?,
    @Json(name = "py") val latitude: Double?,
    @Json(name = "px") val longitude: Double?
)

data class BusLaneModel(
    @Json(name = "cc") val codigoCorredor: Int?,
    @Json(name = "nc") val nomeCorredor: String?
)

data class CompanyModel(
    @Json(name = "hr") val horarioReferencia: String?,
    @Json(name = "e") val areasOperacao: List<OperationAreaModel>?
)

data class OperationAreaModel(
    @Json(name = "a") val codigoArea: Int?,
    @Json(name = "e") val empresas: List<CompanyDataModel>?
)

data class CompanyDataModel(
    @Json(name = "a") val codigoArea: Int?,
    @Json(name = "c") val codigoEmpresa: Int?,
    @Json(name = "n") val nomeEmpresa: String?
)