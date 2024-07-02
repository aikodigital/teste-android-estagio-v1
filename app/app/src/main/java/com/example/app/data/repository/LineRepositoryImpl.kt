package com.example.app.data.repository

import com.example.app.data.api.ServiceAPI
import com.example.app.data.model.AllLinesResponse
import com.example.app.data.model.LineResponse
import com.example.app.domain.repository.LineRepository
import javax.inject.Inject

class LineRepositoryImpl @Inject constructor(
    private val serviceAPI: ServiceAPI
) : LineRepository {
    override suspend fun getLines(): AllLinesResponse {
        return serviceAPI.getLines()
    }

    override suspend fun getLineByCode(code: String): LineResponse {
        return serviceAPI.getLineByCode(code)
    }
}