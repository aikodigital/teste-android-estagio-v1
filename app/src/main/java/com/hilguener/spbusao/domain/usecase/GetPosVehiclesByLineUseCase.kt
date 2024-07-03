package com.hilguener.spbusao.domain.usecase

import com.hilguener.spbusao.data.repository.HttpRepository
import com.hilguener.spbusao.domain.model.Vehicles

class GetPosVehiclesByLineUseCase(
    private val repo: HttpRepository,
) {
    suspend operator fun invoke(
        haveError: (String) -> Unit,
        idLine: Int,
    ): List<Vehicles> {
        var listPosVehicles = listOf<Vehicles>()
        val response = repo.getPosVehiclesByLine(idLine)

        if (response.isSuccessful) {
            listPosVehicles = response.body()?.listOfVehicles ?: emptyList()
        } else {
            haveError(response.message())
        }

        return listPosVehicles
    }
}
