package com.example.app.domain.usecase

import com.example.app.data.model.LineResponse
import com.example.app.domain.repository.LineRepository
import javax.inject.Inject

class GetLineByCodeUseCase @Inject constructor(
    private val lineRepository: LineRepository
) {
    suspend operator fun invoke(code: String): LineResponse {
        return lineRepository.getLineByCode(code)
    }
}