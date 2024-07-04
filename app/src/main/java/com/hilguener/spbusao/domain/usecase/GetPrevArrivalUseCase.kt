package com.hilguener.spbusao.domain.usecase

import com.hilguener.spbusao.data.repository.HttpRepository
import com.hilguener.spbusao.domain.model.PrevArrival

class GetPrevArrivalUseCase(
    private val repo: HttpRepository,
) {
    suspend operator fun invoke(
        hasError: (String) -> Unit,
        id: Int,
    ): PrevArrival? {
        val response = repo.getPrevArrival(id)

        return if (response.isSuccessful) {
            response.body()
        } else {
            hasError(response.message())
            null
        }
    }
}
