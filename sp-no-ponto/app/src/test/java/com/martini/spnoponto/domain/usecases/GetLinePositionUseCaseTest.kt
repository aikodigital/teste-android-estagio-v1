package com.martini.spnoponto.domain.usecases

import com.martini.spnoponto.data.models.linePosition.LinePositionResponse
import com.martini.spnoponto.data.models.linePosition.V
import com.martini.spnoponto.domain.entities.linePosition.LinePosition
import com.martini.spnoponto.domain.entities.linePosition.LinePositionParams
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

class GetLinePositionUseCaseTest {
    private val mock = mockk<TrafficRepository>()

    private val params = LinePositionParams(200)

    private val useCase = GetLinePositionUseCase(mock)

    @Before
    fun setup() {
        coEvery { mock.authorize() } returns Unit
    }

    @Test
    fun shouldReturnFailure() = runBlocking {
        coEvery { mock.getLinePosition(any()) } throws Exception("")

        val response = useCase(params)
        val result = response.last()

        Assert.assertEquals(GetLinePositionState.Failure, result)
    }

    @Test
    fun shouldReturnTimeoutFailure() = runBlocking {
        coEvery { mock.getLinePosition(any()) } throws SocketTimeoutException()

        val response = useCase(params)
        val result = response.last()

        Assert.assertEquals(GetLinePositionState.TimeoutFailure, result)
    }

    @Test
    fun shouldReturnServerFailure() = runBlocking {
        val httpException = HttpException(
            Response.error<ResponseBody>(500 ,
                "some content".toResponseBody("plain/text".toMediaTypeOrNull())
            ))

        coEvery { mock.getLinePosition(any()) } throws httpException

        val response = useCase(params)
        val result = response.last()


        Assert.assertEquals(GetLinePositionState.ServerFailure, result)
    }

    @Test
    fun shouldReturnListOfLinePosition() = runBlocking {
        val item = V(false,"1", 0.1,0.1, "ta")

        val value = LinePositionResponse(
            "1",
            listOf(item)
        )

        coEvery { mock.getLinePosition(any()) } returns value

        val response = useCase(params)
        val result = response.last()

        val actual = (result as GetLinePositionState.Loaded).positions

        val expected = listOf(LinePosition(
            false,
            "1",
            0.1,
            0.1,
            "ta"
        ))

        Assert.assertEquals(expected, actual)
    }
}