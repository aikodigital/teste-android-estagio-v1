package com.example.app.domain.repository

import com.example.app.data.model.AllLinesResponse
import com.example.app.data.model.LineResponse

interface LineRepository {
    suspend fun getLines(): AllLinesResponse
    suspend fun getLineByCode(code: String): List<LineResponse>
}