package com.hilguener.spbusao.domain.usecase

import com.hilguener.spbusao.data.repository.HttpRepository
import com.hilguener.spbusao.domain.model.PosVehicles

class GetPosVehiclesUseCase(
    private val repo: HttpRepository,
) {
    suspend operator fun invoke(
        haveError: (String) -> Unit,
    ): PosVehicles? {
        var listPosVehicles: PosVehicles? = null
        val response = repo.getPosVehicles()

        if (response.isSuccessful) {
            listPosVehicles = response.body()
        } else {
            haveError(response.message())
        }

        return listPosVehicles
    }
}
