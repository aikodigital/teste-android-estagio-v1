package br.com.samuel.testeaiko.core.application.usecases

import br.com.samuel.testeaiko.core.application.repositories.CorridorsRepository
import br.com.samuel.testeaiko.core.domain.dtos.CorridorResponse
import br.com.samuel.testeaiko.util.ResourceResult
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchCorridorsUCTest {

    @Mock
    private lateinit var corridorsRepository: CorridorsRepository

    @Test
    fun testSearchCorridors_Should_return_Corridors() = runBlocking {
        val corridorsResponse = listOf(
            CorridorResponse(8, "Campo Limpo"),
            CorridorResponse(9, "Expresso Tiradentes")
        )

        Mockito.`when`(corridorsRepository.searchCorridors())
            .thenReturn(ResourceResult.Success(corridorsResponse))

        val searchCorridors = SearchCorridorsUC(corridorsRepository)
        val result = searchCorridors()

        Assert.assertNotNull(result.data)
        Assert.assertEquals(2, result.data?.size)
    }

}