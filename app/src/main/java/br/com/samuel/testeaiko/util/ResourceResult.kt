package br.com.samuel.testeaiko.util

sealed class ResourceResult<T>(val data: T? = null, val error: String? = null) {
    class Success<T>(data: T?) : ResourceResult<T>(data)
    class Error<T>(message: String, data: T? = null) : ResourceResult<T>(data, message)
}