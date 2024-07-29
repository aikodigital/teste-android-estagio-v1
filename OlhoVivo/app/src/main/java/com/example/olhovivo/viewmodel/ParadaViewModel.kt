package com.example.olhovivo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.olhovivo.model.Parada
import com.example.olhovivo.repository.ParadaRepository
import kotlinx.coroutines.launch

class ParadaViewModel(private val repository: ParadaRepository) : ViewModel() {

    private val _paradas = MutableLiveData<List<Parada>>()
    val paradas: LiveData<List<Parada>> get() = _paradas

    fun fetchParadas() {
        viewModelScope.launch {
            val result = repository.buscarParadas()
            _paradas.postValue(result)
        }
    }
}
