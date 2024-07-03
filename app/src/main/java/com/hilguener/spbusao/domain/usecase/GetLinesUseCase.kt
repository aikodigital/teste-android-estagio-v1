package com.hilguener.spbusao.domain.usecase

import com.hilguener.spbusao.data.repository.HttpRepository
import com.hilguener.spbusao.domain.model.Lines

class GetLinesUseCase(
    private val repo: HttpRepository,
) {
    suspend operator fun invoke(
        haveError: (String) -> Unit,
        term: String,
    ): List<Lines>? {
        val response = repo.getLines(term)
        var listLines: List<Lines>? = emptyList()

        if (response.isSuccessful) {
            listLines = response.body()
        } else {
            haveError(response.message())
        }

        return listLines
    }
}
