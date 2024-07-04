package com.hilguener.spbusao.domain.usecase

data class BusManagerUseCase(
    val authenticate: AuthenticationUseCase,
    val getPosVehicles: GetPosVehiclesUseCase,
    val getPosVehiclesByLineUseCase: GetPosVehiclesByLineUseCase,
    val getParades: GetParadesUseCase,
    val getParadesByLineUseCase: GetParadesByLineUseCase,
    val getPrevArrival: GetPrevArrivalUseCase,
    val getLines: GetLinesUseCase,
)
