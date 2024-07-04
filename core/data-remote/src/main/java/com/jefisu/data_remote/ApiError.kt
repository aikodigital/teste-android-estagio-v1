package com.jefisu.data_remote

import com.jefisu.domain.util.Error

enum class ApiError : Error {
    AUTHENTICATION_FAILED,
    NO_INTERNET,
    SERVER_ERROR
}