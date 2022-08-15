package com.martini.spnoponto.domain.usecases

import com.martini.spnoponto.data.models.busStop.BusStopResponse
import com.martini.spnoponto.data.models.busStop.BusStopResponseItem
import com.martini.spnoponto.domain.entities.busStop.BusStop
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

class GetBusStopUseCaseTest {
    private val mock = mockk<TrafficRepository>()

    private val useCase = GetBusStopUseCase(mock)

    @Before
    fun setup() {
        coEvery { mock.authorize() } returns Unit
    }

    @Test
    fun shouldReturnFailure() = runBlocking {
        coEvery { mock.getBusStop() } throws Exception()

        val response = useCase()
        val result = response.last()

        Assert.assertEquals(GetBusStopState.Failure, result)
    }

    @Test
    fun shouldReturnTimeoutFailure() = runBlocking {
        coEvery { mock.getBusStop() } throws SocketTimeoutException()

        val response = useCase()
        val result = response.last()

        Assert.assertEquals(GetBusStopState.TimeoutFailure, result)
    }

    @Test
    fun shouldReturnServerFailure() = runBlocking {

        val httpException = HttpException(
            Response.error<ResponseBody>(500 ,
                "some content".toResponseBody("plain/text".toMediaTypeOrNull())
            ))

        coEvery { mock.getBusStop() } throws httpException

        val response = useCase()
        val result = response.last()

        Assert.assertEquals(GetBusStopState.ServerFailure, result)
    }

    @Test
    fun shouldReturnListOfBusStop() = runBlocking {

        val value = BusStopResponse().apply {
            add(BusStopResponseItem(
                200,
                "1",
                "1",
                0.1,
                0.1,
            ))
        }

        coEvery { mock.getBusStop() } returns value

        val response = useCase()
        val result = response.last()

        val actual = (result as GetBusStopState.Loaded).stops
        val expected = listOf(BusStop(
            200,
            "1",
            "1",
            0.1,
            0.1
        ))

        Assert.assertEquals(expected, actual)
    }

}