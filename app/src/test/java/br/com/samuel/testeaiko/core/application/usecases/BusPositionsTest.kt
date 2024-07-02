package br.com.samuel.testeaiko.core.application.usecases

import br.com.samuel.testeaiko.core.application.repositories.PositionsRepository
import br.com.samuel.testeaiko.core.domain.dtos.BusLinePositionResponse
import br.com.samuel.testeaiko.core.domain.dtos.BusResponse
import br.com.samuel.testeaiko.util.ResourceResult
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.time.LocalTime
import java.time.ZonedDateTime

@RunWith(MockitoJUnitRunner::class)
class BusPositionsTest {

    @Mock
    private lateinit var positionsRepository: PositionsRepository

    @Test
    fun testSearchBusPositionsByLine_Should_return_Positions() = runBlocking {
        val positionsResponse = BusLinePositionResponse(
            LocalTime.now(),
            listOf(
                BusResponse(12690, true, ZonedDateTime.parse("2024-06-30T23:20:04Z"), -23.530192125, -46.666985249999996, null, null),
                BusResponse(12678, true, ZonedDateTime.parse("2024-06-30T23:19:40Z"), -23.547722, -46.641317, null, null),
            )
        )

        Mockito.`when`(positionsRepository.searchByLine(2506))
            .thenReturn(ResourceResult.Success(positionsResponse))

        val searchPositionsByLine = SearchBusByLineUC(positionsRepository)
        val result = searchPositionsByLine(2506)

        Assert.assertNotNull(result.data)
        Assert.assertEquals(2, result.data?.size)
    }

}