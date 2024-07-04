package com.example.aiko.data.repository

import com.example.aiko.data.remote.ApiClient

class PositionRepository() {

    private val positionRepository = ApiClient.apiService

    suspend fun getPosition() = positionRepository.getPosition()

    suspend fun getStopBus() = positionRepository.getStopBus()

    suspend fun postAuth() = positionRepository.postAuth()
}