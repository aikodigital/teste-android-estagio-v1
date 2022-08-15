package com.martini.spnoponto.domain.usecases


import com.martini.spnoponto.domain.entities.json.JsonConverterLinha
import com.martini.spnoponto.domain.entities.lineSearch.Linha
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Assert
import org.junit.Test

class LineDetailsUseCaseTest {

    private val jsonMock = mockk<JsonConverterLinha>()

    private val linha = Linha(
        200,
        false,
        "1",
        1,
        1,
        "1",
        "1"
    )

    private val linhaString: String = Json.encodeToString(linha)
    private val lineDetailsUseCase = LineDetailsUseCase(jsonMock)

    @Test
    fun shouldReturnFailureOnAnUnknownException() = runBlocking {
        coEvery { jsonMock.fromString(any()) } throws Exception("any")

        val response = lineDetailsUseCase(linhaString)
        val result = response.last()

        Assert.assertEquals(LineDetailsState.Failure, result)
    }

    @Test
    fun shouldReturnInvalidArgumentFailure() = runBlocking {
        coEvery { jsonMock.fromString(any()) } returns linha

        val response = lineDetailsUseCase(null)
        val result = response.last()

        Assert.assertEquals(LineDetailsState.InvalidArgumentFailure, result)
    }

    @Test
    fun shouldReturnLinha() = runBlocking {
        coEvery { jsonMock.fromString(any()) } returns linha

        val response = lineDetailsUseCase(linhaString)
        val result = response.last()

        val expected = linha
        val actual = (result as LineDetailsState.Loaded).linha

        Assert.assertEquals(expected, actual)
    }
}