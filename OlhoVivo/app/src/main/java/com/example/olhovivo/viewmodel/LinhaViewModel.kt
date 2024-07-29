package com.example.olhovivo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.olhovivo.model.Linha
import com.example.olhovivo.repository.LinhaRepository
import kotlinx.coroutines.launch

class LinhaViewModel(private val repository: LinhaRepository) : ViewModel() {
    private val _linhas = MutableLiveData<List<Linha>>()
    val linhas: LiveData<List<Linha>> get() = _linhas

    fun fetchLinhas(termosBusca: String) {
        viewModelScope.launch {
            val result = repository.getLinhas(termosBusca)
            _linhas.postValue(result)
        }
    }
}
