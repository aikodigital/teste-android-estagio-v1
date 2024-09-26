package com.aiko.domain.network

sealed class NetworkResult<T : Any> {
    class Success<T: Any>(val data: T) : NetworkResult<T>()
    class Error<T: Any>(val code: Int, val body: ErrorBody?) : NetworkResult<T>()
    class Exception<T: Any>(val e: Throwable) : NetworkResult<T>()
}

data class ErrorBody(val error: String)

/**
*   CallBack to handle with Success
*/
suspend fun <T : Any> NetworkResult<T>.onSuccess(
    executable: suspend (T) -> Unit
): NetworkResult<T> = apply {
    if (this is NetworkResult.Success<T>) {
        executable(data)
    }
}

/**
 *   CallBack to handle with Error
 */
suspend fun <T : Any> NetworkResult<T>.onError(
    executable: suspend () -> Unit
): NetworkResult<T> = apply {
    if (this is NetworkResult.Error<T>) {
        executable()
    }
}


/**
 *   CallBack to handle with Exception
 */
suspend fun <T : Any> NetworkResult<T>.onException(
    executable: suspend (e: Throwable) -> Unit
): NetworkResult<T> = apply {
    if (this is NetworkResult.Exception<T>) {
        executable(e)
    }
}