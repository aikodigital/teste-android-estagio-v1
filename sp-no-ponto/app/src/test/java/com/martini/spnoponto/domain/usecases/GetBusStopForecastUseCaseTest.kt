package com.martini.spnoponto.domain.usecases

import com.martini.spnoponto.common.NoBusPointInfoException
import com.martini.spnoponto.data.models.forecast.BusStopForecastResponse
import com.martini.spnoponto.data.models.forecast.P
import com.martini.spnoponto.domain.entities.busStop.BusStop
import com.martini.spnoponto.domain.entities.busStopForecast.BusStopForecast
import com.martini.spnoponto.domain.entities.busStopForecast.BusStopForecastParams
import com.martini.spnoponto.domain.entities.busStopForecast.BusStopPoint
import com.martini.spnoponto.domain.repositories.TrafficRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketTimeoutException

class GetBusStopForecastUseCaseTest {

    private val mock = mockk<TrafficRepository>()

    private val useCase = GetBusStopForecastUseCase(mock)

    private val params = BusStopForecastParams(
        BusStop(
            200,
            "1",
            "1",
            0.1,
            0.1
        )
    )

    private val busStopPoint = BusStopPoint(
        200,
        listOf(),
        "1",
        0.1,
        0.1
    )

    @Before
    fun setup() {
        coEvery { mock.authorize() } returns Unit
    }

    @Test
    fun shouldReturnFailureWhenAnUnknownExceptionIsThrown() = runBlocking {
        coEvery { mock.getBusStopForecast(any()) } throws Exception()

        val response = useCase(params)

        val actual = response.last()
        val expected = GetBusStopForecastState.Failure

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun shouldReturnTimeoutFailureWhenSocketTimeoutExceptionIsThrown() = runBlocking {
        coEvery { mock.getBusStopForecast(any()) } throws SocketTimeoutException()

        val response = useCase(params)

        val actual = response.last()
        val expected = GetBusStopForecastState.TimeoutFailure

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun shouldReturnServerFailureHttpExceptionIsThrown() = runBlocking {
        val httpException = HttpException(
            Response.error<ResponseBody>(500 ,
                "some content".toResponseBody("plain/text".toMediaTypeOrNull())
            ))

        coEvery { mock.getBusStopForecast(any()) } throws httpException

        val response = useCase(params)

        val actual = response.last()
        val expected = GetBusStopForecastState.ServerFailure

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun shouldReturnNoBusInfoFailureWhenAnNoBusInfoExceptionIsThrown() = runBlocking {
        coEvery { mock.getBusStopForecast(any()) } throws NoBusPointInfoException()

        val response = useCase(params)

        val actual = response.last()
        val expected = GetBusStopForecastState.NoBusInfoFailure

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun shouldReturnBusStopPointWhenNoExceptionIsThrown() = runBlocking {

        val value = BusStopForecastResponse(
            "1",
            P(200, listOf(),"1",0.1,0.1)
        )

        coEvery { mock.getBusStopForecast(any()) } returns value

        val response = useCase(params)
        val actual = (response.last() as GetBusStopForecastState.Loaded).busStopPoint
        val expected = GetBusStopForecastState.Loaded(BusStopForecast(
            "1",
            busStopPoint
        ))

        Assert.assertEquals(expected.busStopPoint, actual)
    }
}