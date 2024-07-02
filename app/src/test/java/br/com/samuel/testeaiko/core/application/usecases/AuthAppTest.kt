package br.com.samuel.testeaiko.core.application.usecases

import br.com.samuel.testeaiko.core.application.repositories.AuthRepository
import br.com.samuel.testeaiko.util.ResourceResult
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class AuthAppTest {

    @Test
    fun testAuthApp_Should_return_True() = runBlocking {
        val authenticate = AuthenticateAppUC(AuthRepositoryImpl())
        val result = authenticate()

        Assert.assertNotNull(result.data)
        Assert.assertTrue(result.data!!)
    }

    internal class AuthRepositoryImpl : AuthRepository {

        override suspend fun auth(token: String): ResourceResult<Boolean> {
            return ResourceResult.Success(true)
        }

    }

}