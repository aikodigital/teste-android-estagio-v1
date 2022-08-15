package com.martini.spnoponto.domain.usecases


import com.martini.spnoponto.data.models.line.LineSearchResponse
import com.martini.spnoponto.data.models.line.LineSearchResponseItem
import com.martini.spnoponto.data.models.line.toLinha
import com.martini.spnoponto.domain.entities.lineSearch.SearchLineParams
import com.martini.spnoponto.domain.entities.settings.Filter
import com.martini.spnoponto.domain.repositories.TrafficRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.*
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

class SearchLineUseCaseTest {

    private val trafficRepositoryMock = mockk<TrafficRepository>()

    private val params = SearchLineParams("text")

    private val item = LineSearchResponseItem(
        200,
        false,
        "lt",
        200,
        200,
        "tp",
        "ts"
    )

    private val value = LineSearchResponse().apply {
        add(item)
    }

    val searchLineUseCase = SearchLineUseCase(trafficRepositoryMock)

    private val response = searchLineUseCase(params)

    @Before
    fun setup() {
        coEvery { trafficRepositoryMock.authorize() } returns Unit
        coEvery { trafficRepositoryMock.getFilterSettings() } returns Filter.Todos
    }

    @Test
    fun shouldReturnFailureOnAnUnknownException() = runBlocking {

        coEvery { trafficRepositoryMock.searchLine(any()) } throws Exception("any")

        val result = response.last()

        Assert.assertEquals(result, SearchLineState.Failure)
    }

    @Test
    fun shouldReturnTimeoutFailureOnASocketTimeoutException() = runBlocking {
        coEvery { trafficRepositoryMock.searchLine(any()) } throws SocketTimeoutException()

        val result = response.last()

        Assert.assertEquals(result, SearchLineState.TimeoutFailure)
    }

    @Test
    fun shouldReturnServerFailureOnAHttpException() = runBlocking {

        val httpException = HttpException(Response.error<ResponseBody>(500 ,
            "some content".toResponseBody("plain/text".toMediaTypeOrNull())
        ))

        coEvery { trafficRepositoryMock.searchLine(any()) } throws httpException

        val result = response.last()

        Assert.assertEquals(SearchLineState.ServerFailure, result)
    }

    @Test
    fun shouldReturnLoadedOnSuccessCall() = runBlocking {

        val expected = listOf(item.toLinha())

        coEvery { trafficRepositoryMock.searchLine(any()) } returns value

        val result = response.last()

        Assert.assertEquals((result as SearchLineState.Loaded).lines, expected)
    }
}