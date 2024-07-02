package br.com.samuel.testeaiko.core.application.usecases

import br.com.samuel.testeaiko.BuildConfig
import br.com.samuel.testeaiko.core.application.repositories.AuthRepository
import br.com.samuel.testeaiko.util.ResourceResult
import javax.inject.Inject

class AuthenticateAppUC @Inject constructor(private val authenticationRepository: AuthRepository) {

    suspend operator fun invoke(): ResourceResult<Boolean,> {
        return authenticationRepository.auth(BuildConfig.OLHO_VIVO_API_KEY)
    }

}