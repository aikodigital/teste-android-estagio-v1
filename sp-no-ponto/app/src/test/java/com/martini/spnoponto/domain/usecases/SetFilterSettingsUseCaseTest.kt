package com.martini.spnoponto.domain.usecases

import com.martini.spnoponto.domain.entities.settings.Filter
import com.martini.spnoponto.domain.entities.settings.SetFilterSettingsParams
import com.martini.spnoponto.domain.repositories.TrafficRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class SetFilterSettingsUseCaseTest {

    private val mock = mockk<TrafficRepository>()

    private val useCase = SetFilterSettingsUseCase(mock)

    private val params = SetFilterSettingsParams(
        Filter.Secundario
    )

    @Test
    fun shouldReturnFailureIfAnUnknownExceptionIsThrown() = runBlocking {
        coEvery { mock.setFilterSettings(any()) } throws Exception("")

        val response = useCase(params)
        val actual = response.last()
        val expected = SetFilterSettingsState.Failure

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun shouldReturnNothingIfNoExceptionIsThrown() = runBlocking {
        coEvery { mock.setFilterSettings(any()) } returns Unit

        val response = useCase(params)
        val actual = response.last()
        val expected = SetFilterSettingsState.Loaded

        Assert.assertEquals(expected, actual)
    }
}