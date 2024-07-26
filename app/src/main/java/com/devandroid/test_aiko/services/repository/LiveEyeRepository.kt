package com.devandroid.test_aiko.services.repository

import com.devandroid.test_aiko.models.Line
import com.devandroid.test_aiko.services.ApiService

class LiveEyeRepository(private val apiService : ApiService) {

    private val token = "5cd400e8ad8211ebb5ab33097103b867c53e4baa6d8aaceefd447d8621b8e87f"

    suspend fun authenticate(): Boolean {
        val response = apiService.authenticate(token)
        return response.body() ?: false
    }

    suspend fun searchLine(term: String): List<Line>? {
        val response = apiService.searchLine(term)
        return if (response.isSuccessful) response.body() else null
    }
}