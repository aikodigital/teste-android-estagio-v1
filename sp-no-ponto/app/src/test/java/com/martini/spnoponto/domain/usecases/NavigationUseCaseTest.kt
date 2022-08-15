package com.martini.spnoponto.domain.usecases

import com.martini.spnoponto.domain.entities.json.JsonConverterLinha
import com.martini.spnoponto.domain.entities.lineSearch.Linha
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class NavigationUseCaseTest {
    private val mock = mockk<JsonConverterLinha>()

    private val useCase = NavigationUseCase(mock)

    private val linha = Linha(
        0,
        false,
        "1",
        1,
        1,
        "1",
        "1"
    )

    @Test
    fun shouldReturnFailureIfJsonFails() = runBlocking {
        coEvery { mock.fromString(any()) } throws Exception()

        val response = useCase.navigateToLineDetails(linha)

        val actual = response.last()

        Assert.assertEquals(NavigationAction.Failure, actual)
    }

    @Test
    fun shouldReturnNavigateToLineDetails() = runBlocking {

        val realJson = JsonConverterLinha()
        val realUseCase = NavigationUseCase(realJson)

        val linhaString = realJson.toString(linha)

        val response = realUseCase.navigateToLineDetails(linha)

        val actual = (response.last() as NavigationAction.NavigateToLineDetails).line
        val expected = NavigationAction.NavigateToLineDetails(linhaString).line

        Assert.assertEquals(expected, actual)
    }
}