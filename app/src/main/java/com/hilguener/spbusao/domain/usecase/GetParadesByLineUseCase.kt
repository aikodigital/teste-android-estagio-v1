package com.hilguener.spbusao.domain.usecase

import com.hilguener.spbusao.data.repository.HttpRepository
import com.hilguener.spbusao.domain.model.Parades

class GetParadesByLineUseCase(
    private val repo: HttpRepository,
) {
    suspend operator fun invoke(
        haveError: (String) -> Unit,
        idLine: Int,
    ): List<Parades> {
        var listParades = listOf<Parades>()
        val response = repo.getParadesByLine(idLine)

        if (response.isSuccessful) {
            listParades = response.body() ?: emptyList()
        } else {
            haveError(response.message())
        }

        return listParades
    }
}
