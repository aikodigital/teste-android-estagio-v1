package br.com.samuel.testeaiko.core.application.strategies

import br.com.samuel.testeaiko.core.application.repositories.StopsRepository
import br.com.samuel.testeaiko.core.domain.dtos.StopResponse
import br.com.samuel.testeaiko.infra.strategies.SearchStopsByCorridorStrategy
import br.com.samuel.testeaiko.infra.strategies.SearchStopsByLineStrategy
import br.com.samuel.testeaiko.util.ResourceResult
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchStopsStrategyTest {

    @Mock
    private lateinit var stopsRepository: StopsRepository

    @Test
    fun testSearchStopsBy_ExistingLine_Should_return_Stops() = runBlocking {
        Mockito.`when`(stopsRepository.searchStopsByLine(1))
            .thenReturn(ResourceResult.Success(stopsResponse))

        val searchStrategy: SearchStopsStrategy = SearchStopsByLineStrategy(stopsRepository)
        val result = searchStrategy.getStops(1)

        Assert.assertNotNull(result.data)
        Assert.assertEquals(2, result.data?.size)
    }

    @Test
    fun testSearchStopsBy_NotExistingLine_Should_return_Stops() = runBlocking {
        Mockito.`when`(stopsRepository.searchStopsByLine(10))
            .thenReturn(ResourceResult.Success(emptyList()))

        val searchStrategy: SearchStopsStrategy = SearchStopsByLineStrategy(stopsRepository)
        val result = searchStrategy.getStops(10)

        Assert.assertNotNull(result.data)
        Assert.assertEquals(0, result.data?.size)
    }

    @Test
    fun testSearchStopsBy_ExistingCorridor_Should_return_Stops() = runBlocking {
        Mockito.`when`(stopsRepository.searchStopsByCorridor(1))
            .thenReturn(ResourceResult.Success(stopsResponse))

        val searchStrategy: SearchStopsStrategy = SearchStopsByCorridorStrategy(stopsRepository)
        val result = searchStrategy.getStops(1)

        Assert.assertNotNull(result.data)
        Assert.assertEquals(2, result.data?.size)
    }

    @Test
    fun testSearchStopsBy_NotExistingCorridor_Should_return_Stops() = runBlocking {
        Mockito.`when`(stopsRepository.searchStopsByCorridor(10))
            .thenReturn(ResourceResult.Success(emptyList()))

        val searchStrategy: SearchStopsStrategy = SearchStopsByCorridorStrategy(stopsRepository)
        val result = searchStrategy.getStops(10)

        Assert.assertNotNull(result.data)
        Assert.assertEquals(0, result.data?.size)
    }

    companion object {
        private val stopsResponse = listOf(
            StopResponse(
                290001401,
                "FREGUESIA B/C",
                "R CESARE BADIALI/ R ENRICO CARAFA",
                -23.494786,
                -46.708775
            ),
            StopResponse(
                480012877,
                "MARGINAL B/C",
                "AC PONTE DO PIQUERI AV EMB MACEDO SOARES/ R PROFESSORA SURAIA AIDAR MENON",
                -23.511052,
                -46.705493
            ),
        )
    }

}