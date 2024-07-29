package com.example.olhovivo.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ViewModelLinhasTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: LinhaRepository

    private lateinit var viewModel: LinhaViewModel

    @Mock
    private lateinit var linhasObserver: Observer<List<Linha>>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = ViewModelLinhas(repository)
    }

    @Test
    fun `test carregarLinhas`() {
        val mockLinhas = listOf(Linha("123", "Linha Teste"))

        Mockito.`when`(repository.obterLinhas()).thenReturn(mockLinhas)

        viewModel.linhas.observeForever(linhasObserver)
        viewModel.carregarLinhas()

        Mockito.verify(linhasObserver).onChanged(mockLinhas)
    }
}
