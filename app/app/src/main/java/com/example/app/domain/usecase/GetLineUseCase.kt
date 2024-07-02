package com.example.app.domain.usecase

import com.example.app.data.mapper.toDomain
import com.example.app.domain.model.AllLines
import com.example.app.domain.repository.LineRepository
import javax.inject.Inject

class GetLineUseCase @Inject constructor(
    private val lineRepository: LineRepository
) {
    suspend operator fun invoke(): AllLines {
        return lineRepository.getLines().toDomain()
    }
}