package com.aiko.domain.usecase.base


/**
 * Representa o resultado de uma operação, podendo ser um sucesso ou uma falha.
 *
 * @param T Tipo do dado retornado em caso de sucesso.
 */
sealed class DataResult<out T> {

    /**
     * Representa o sucesso de uma operação.
     *
     * @param data Dado retornado pela operação.
     */
    class Success<out T>(val data: T) : DataResult<T>()

    /**
     * Representa a falha de uma operação.
     *
     * @param throwable Erro ocorrido durante a operação.
     */
    class Failure(val throwable: Throwable?) : DataResult<Nothing>()

    /**
     * Verifica se o resultado foi um sucesso.
     */
    val isSuccess get() = this is Success<T>

    /**
     * Verifica se o resultado foi uma falha.
     */
    val isFailure get() = this is Failure

    /**
     * Obtém o dado de sucesso, se o resultado for [Success].
     */
    val success get() = (this as Success<T>)

    /**
     * Lida com o resultado da operação, executando funções para sucesso ou erro.
     *
     * @param success Função a ser executada em caso de sucesso.
     * @param error Função a ser executada em caso de falha.
     */
    fun handleResult(success: (T) -> Unit, error: (Throwable?) -> Unit) {
        when (this) {
            is Success -> success(data)
            is Failure -> error(throwable)
        }
    }
}

/**
 * Extensão para lidar com o sucesso de uma operação de forma suspensa.
 *
 * @param executable Função suspensa a ser executada em caso de sucesso.
 * @return O [DataResult] original.
 */
suspend fun <T> DataResult<T>.onSuccess(
    executable: suspend (T) -> Unit
): DataResult<T> = apply {
    if (this is DataResult.Success<T>) {
        executable(data)
    }
}

/**
 * Extensão para lidar com a falha de uma operação de forma suspensa.
 *
 * @param executable Função suspensa a ser executada em caso de falha.
 * @return O [DataResult] original.
 */
suspend fun <T> DataResult<T>.onFailure(
    executable: suspend (Throwable?) -> Unit
): DataResult<T> = apply {
    if (this is DataResult.Failure) {
        executable(throwable)
    }
}