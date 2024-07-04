package com.exemple.urbanbus.data.repositories.authenticate

interface AuthenticateRepository {
    suspend fun authenticate(): String
}