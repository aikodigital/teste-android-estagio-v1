package com.example.app.domain.usecase

import com.example.app.data.mapper.toDomain
import com.example.app.domain.model.StopPoint
import com.example.app.domain.repository.StopPointRepository
import javax.inject.Inject

class GetStopPointByLineUseCase @Inject constructor(
    private val stopPointRepository: StopPointRepository
) {
    suspend operator fun invoke(code: String): List<StopPoint> {
        return stopPointRepository.getStopPointByLine(code).toDomain()
    }
}