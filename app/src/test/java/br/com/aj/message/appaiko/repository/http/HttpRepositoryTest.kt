package br.com.aj.message.appaiko.repository.http

import br.com.aj.message.appaiko.httpModulesRepo
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.koin.test.mock.MockProviderRule

class HttpRepositoryTest :KoinTest {

    val repository by inject<HttpRepository>()


    @Before
    fun before() {

    }

    @After
    fun after() {

    }

    @Test
    fun getAllPositionVehicles() {
    }

    @Test
    suspend fun getAuth() {
      val  auth =  repository.getAuth();
        assertEquals(auth,true )
    }

    @Test
    fun corredor() {
    }

    @Test
    fun buscarParadasPorCorredor() {
    }

    @Test
    fun previsionBusParada() {
    }
}