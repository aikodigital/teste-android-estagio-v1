package com.aiko.domain.usecase.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow

/**
 * Classe base para casos de uso que retornam um [DataResult].
 *
 * @param Params Tipo do parâmetro de entrada.
 * @param R Tipo do resultado.
 */
abstract class BaseUseCase<in Params, out R> {
    private val superVisorJob = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + superVisorJob)

    /**
     * Implementa a lógica do caso de uso.
     *
     * @param value O parâmetro de entrada.
     * @return [DataResult] com o resultado da operação.
     */
    protected abstract suspend fun doWork(value: Params): DataResult<R>

    /**
     * Executa o caso de uso e retorna o resultado.
     *
     * @param value O parâmetro de entrada.
     * @return [DataResult] com o resultado da operação.
     */
    suspend fun execute(value: Params): DataResult<R> {
        return withContext(scope.coroutineContext) {
            try {
                withContext(Dispatchers.IO) { doWork(value) }
            } catch (e: Throwable) {
                DataResult.Failure(e)
            }
        }
    }

    /**
     * Cancela qualquer trabalho em andamento associado a este caso de uso.
     */
    fun cancelWork() = scope.coroutineContext.cancelChildren()
}

/**
 * Classe base para casos de uso que retornam um [Flow] de [DataResult].
 *
 * @param Params Tipo do parâmetro de entrada.
 * @param R Tipo do resultado.
 */
abstract class BaseUseCaseFlow<in Params, out R> {
    private val superVisorJob = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + superVisorJob)

    /**
     * Implementa a lógica do caso de uso.
     *
     * @param value O parâmetro de entrada.
     * @return O resultado da operação.
     */
    protected abstract suspend fun doWork(value: Params): R

    /**
     * Executa o caso de uso e retorna um fluxo de [DataResult].
     *
     * @param value O parâmetro de entrada.
     * @return [Flow] de [DataResult] com o resultado da operação.
     */
    fun execute(value: Params): Flow<DataResult<R>> = channelFlow {
        withContext(scope.coroutineContext) {
            try {
                val result = withContext(Dispatchers.IO) {
                    doWork(value)
                }
                send(DataResult.Success(result))
            } catch (e: Exception) {
                send(DataResult.Failure(e))
            }
        }
    }

    /**
     * Cancela qualquer trabalho em andamento associado a este caso de uso.
     */
    fun cancelWork() = scope.coroutineContext.cancelChildren()
}