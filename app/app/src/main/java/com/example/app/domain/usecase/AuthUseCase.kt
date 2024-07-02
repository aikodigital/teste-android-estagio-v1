package com.example.app.domain.usecase

import com.example.app.network.ServiceProvider
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val serviceProvider: ServiceProvider
) {
    suspend operator fun invoke(token: String): Boolean {
        return serviceProvider.authenticate(token)
    }
}