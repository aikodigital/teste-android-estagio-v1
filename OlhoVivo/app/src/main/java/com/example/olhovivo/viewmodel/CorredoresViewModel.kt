package com.example.olhovivo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.olhovivo.model.Corredor
import com.example.olhovivo.repository.CorredoresRepository
import kotlinx.coroutines.launch

class ViewModelCorredores(private val repository: CorredoresRepository) : ViewModel() {
    val corredores = MutableLiveData<List<Corredor>>()

    fun carregarCorredores() {
        viewModelScope.launch {
            val result = repository.obterCorredores()
            corredores.postValue(result)
        }
    }
}
