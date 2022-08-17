package br.com.aj.message.appaiko.util

import kotlinx.coroutines.flow.Flow

/**
 * interface de implementação que observa a conexão com internet
 */
interface ConnectionManager {

    fun observe() : Flow<Status>

    enum class Status {
        CONNECTED,
        NOT_CONNECTED
    }



}