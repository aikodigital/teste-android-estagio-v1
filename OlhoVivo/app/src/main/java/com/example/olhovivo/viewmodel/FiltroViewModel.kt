package com.example.olhovivo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.olhovivo.model.ResultadoPesquisa
import com.example.olhovivo.repository.FiltroRepository
import kotlinx.coroutines.launch

class FiltroViewModel(private val repository: FiltroRepository) : ViewModel() {
    private val _resultadosPesquisa = MutableLiveData<List<ResultadoPesquisa>>()
    val resultadosPesquisa: LiveData<List<ResultadoPesquisa>> = _resultadosPesquisa

    fun pesquisar(term: String, filtros: Map<String, Any>) {
        viewModelScope.launch {
            try {
                val resultados = repository.pesquisar(term, filtros)
                _resultadosPesquisa.postValue(resultados)
            } catch (e: Exception) {
                _resultadosPesquisa.postValue(emptyList())
            }
        }
    }
}
