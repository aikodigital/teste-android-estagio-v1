package com.exemple.urbanbus.utils

// movimenta dados entre as camadas
sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    sealed class Failure : UiState<Nothing>() {
        data class NetworkError(val error: String) : Failure()
        data class HttpError(val error: String) : Failure()
        data class UnknownError(val error: String) : Failure()
    }
}