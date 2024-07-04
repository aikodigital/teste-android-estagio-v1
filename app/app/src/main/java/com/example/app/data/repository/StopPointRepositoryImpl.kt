package com.example.app.data.repository

import com.example.app.data.api.ServiceAPI
import com.example.app.data.model.ApiResponse
import com.example.app.data.model.StopPointResponse
import com.example.app.domain.repository.StopPointRepository
import javax.inject.Inject

class StopPointRepositoryImpl @Inject constructor(
    private val serviceAPI: ServiceAPI
) : StopPointRepository {
    override suspend fun getStopPointByLine(code: String): List<StopPointResponse> {
        return serviceAPI.getStopPointByLine(code)
    }

    override suspend fun getBusByStopPoint(code: String): ApiResponse {
        return serviceAPI.getBusByStopPoint(code)
    }
}