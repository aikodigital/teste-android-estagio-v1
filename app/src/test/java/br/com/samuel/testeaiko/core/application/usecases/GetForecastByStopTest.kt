package br.com.samuel.testeaiko.core.application.usecases

import br.com.samuel.testeaiko.core.application.repositories.ArrivalForecastRepository
import br.com.samuel.testeaiko.core.domain.dtos.ArrivalForecastStopResponse
import br.com.samuel.testeaiko.core.domain.dtos.BusForecastResponse
import br.com.samuel.testeaiko.core.domain.dtos.LineForecastResponse
import br.com.samuel.testeaiko.core.domain.dtos.StopForecastResponse
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
class GetForecastByStopTest {

    @Mock
    private lateinit var arrivalForecastRepository: ArrivalForecastRepository

    @Test
    fun testGetBusForecastByStopCode_Should_return_Forecasts() = runBlocking {
        val forecastResponse = ArrivalForecastStopResponse(
            LocalTime.now(),
            StopForecastResponse(
                640001414,
                "PIQUERI B/C",
                -23.50616,
                -46.706061,
                listOf(
                    LineForecastResponse(
                        "129F-10",
                        2304,
                        1,
                        "METRÔ BARRA FUNDA",
                        "CONEXÃO PETRÔNIO PORTELA",
                        1,
                        listOf(
                            BusForecastResponse(
                                11046,
                                LocalTime.now(),
                                true,
                                ZonedDateTime.now(),
                                -23.498021666666666,
                                -46.70594833333333,
                                null,
                                null
                            )
                        )
                    )
                )
            )
        )

        Mockito.`when`(arrivalForecastRepository.getForecastByStop(640001414))
            .thenReturn(ResourceResult.Success(forecastResponse))

        val getByStopCode = GetForecastByStopUC(arrivalForecastRepository)
        val result = getByStopCode(640001414)

        Assert.assertNotNull(result.data)
        Assert.assertEquals(1, result.data?.size)
    }

}