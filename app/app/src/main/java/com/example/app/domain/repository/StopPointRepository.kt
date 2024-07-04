package com.example.app.domain.repository

import com.example.app.data.model.StopPointResponse

interface StopPointRepository {
    suspend fun getStopPointByLine(code: String): List<StopPointResponse>
}