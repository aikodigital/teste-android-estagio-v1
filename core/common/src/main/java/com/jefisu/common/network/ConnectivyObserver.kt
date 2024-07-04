package com.jefisu.common.network

import kotlinx.coroutines.flow.Flow

sealed interface ConnectivityObserver {
    fun observe(): Flow<Status>

    enum class Status {
        Available, Unavailable, Losing, Lost
    }
}

