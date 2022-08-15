package com.martini.spnoponto.domain.usecases

import com.martini.spnoponto.domain.entities.settings.Filter
import com.martini.spnoponto.domain.repositories.TrafficRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class GetFilterSettingsUseCaseTest {
    private val mock = mockk<TrafficRepository>()

    private val useCase = GetFilterSettingUseCase(mock)

    @Test
    fun shouldReturnFailureOnAnUnknownException() = runBlocking {
        coEvery { mock.getFilterSettings() } throws Exception()

        val response = useCase()

        val expected = GetFilterSettingState.Failure
        val actual = response.last()

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun shouldReturnFilterIfNoExceptionIsThrown() = runBlocking {
        coEvery { mock.getFilterSettings() } returns Filter.Secundario

        val response = useCase()

        val expected = GetFilterSettingState.Loaded(Filter.Secundario).filter
        val actual = (response.last() as GetFilterSettingState.Loaded).filter

        Assert.assertEquals(expected, actual)
    }
}