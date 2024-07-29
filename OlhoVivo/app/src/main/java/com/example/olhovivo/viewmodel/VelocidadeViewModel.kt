package com.example.olhovivo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.olhovivo.repository.VelocidadeRepository
import kotlinx.coroutines.launch
import java.io.File

class ViewModelVelocidade(private val repository: VelocidadeRepository) : ViewModel() {
    val mapaVelocidade = MutableLiveData<File>()

    fun carregarMapaVelocidade(sentido: String? = null) {
        viewModelScope.launch {
            val result = repository.obterMapaVelocidade(sentido)
            mapaVelocidade.postValue(result)
        }
    }
}
