package br.com.samuel.testeaiko.core.application.usecases

import br.com.samuel.testeaiko.core.application.repositories.LinesRepository
import br.com.samuel.testeaiko.core.domain.dtos.LineResponse
import br.com.samuel.testeaiko.core.domain.enums.BusLineDirections
import br.com.samuel.testeaiko.util.ResourceResult
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchLinesTest {

    @Mock
    private lateinit var linesRepository: LinesRepository

    @Test
    fun testSearchLinesByTermAndDirectionPrincipalToSecondary_return_An_Array_Of_Lines() = runBlocking {
        val linesResponse = listOf(
            LineResponse(35274, false, "8000", 2, 1, "PÇA. RAMOS DE AZEVEDO", "TERM. LAPA"),
            LineResponse(34041, false, "8000", 2, 10, "PÇA. RAMOS DE AZEVEDO", "TERM. LAPA"),
            LineResponse(34019, false, "8001", 2, 10, "TERM. LAPA", "VL. PIAUÍ"),
            LineResponse(34020, false, "8002", 2, 10, "TERM. LAPA", "TERM. PIRITUBA")
        )

        Mockito.`when`(
            linesRepository.searchDirection(
                "LAPA",
                BusLineDirections.PRINCIPAL_TO_SECONDARY
            )
        ).thenReturn(ResourceResult.Success(linesResponse))

        val searchByDirection = SearchLineByDirectionUC(linesRepository)
        val result = searchByDirection("LAPA", BusLineDirections.PRINCIPAL_TO_SECONDARY)

        Assert.assertNotNull(result.data)
        Assert.assertEquals(4, result.data?.size)
    }

    @Test
    fun testSearchLinesByTermAndDirectionSecondaryToPrincipal_return_An_Array_Of_Lines() = runBlocking {
        val linesResponse = listOf(
            LineResponse(35274, false, "8000", 2, 1, "PÇA. RAMOS DE AZEVEDO", "TERM. LAPA"),
            LineResponse(34041, false, "8000", 2, 10, "PÇA. RAMOS DE AZEVEDO", "TERM. LAPA"),
            LineResponse(34019, false, "8001", 2, 10, "TERM. LAPA", "VL. PIAUÍ"),
            LineResponse(34020, false, "8002", 2, 10, "TERM. LAPA", "TERM. PIRITUBA")
        )

        Mockito.`when`(
            linesRepository.searchDirection(
                "LAPA",
                BusLineDirections.SECONDARY_TO_PRINCIPAL
            )
        ).thenReturn(ResourceResult.Success(linesResponse))

        val searchByDirection = SearchLineByDirectionUC(linesRepository)
        val result = searchByDirection("LAPA", BusLineDirections.SECONDARY_TO_PRINCIPAL)

        Assert.assertNotNull(result.data)
        Assert.assertEquals(4, result.data?.size)
    }

}