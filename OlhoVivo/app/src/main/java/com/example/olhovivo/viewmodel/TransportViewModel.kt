package com.example.olhovivo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.olhovivo.model.Linha
import com.example.olhovivo.model.Parada
import com.example.olhovivo.model.Veiculo
import com.exemplo.seuprojeto.model.Veiculo
import com.exemplo.seuprojeto.model.Linha
import com.exemplo.seuprojeto.model.Parada
import com.exemplo.seuprojeto.network.RetrofitInstance
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TransportViewModel : ViewModel() {
    private val _veiculos = MutableLiveData<List<Veiculo>>()
    val veiculos: LiveData<List<Veiculo>> get() = _veiculos

    private val _linhas = MutableLiveData<List<Linha>>()
    val linhas: LiveData<List<Linha>> get() = _linhas

    private val _paradas = MutableLiveData<List<Parada>>()
    val paradas: LiveData<List<Parada>> get() = _paradas

    init {
        startFetchingData()
    }

    private fun startFetchingData() {
        viewModelScope.launch {
            while (true) {
                carregarVeiculos()
                delay(60000)
            }
        }
    }

    fun carregarVeiculos() {
        viewModelScope.launch {
            try {
                val response = repository.getPosicoes()
                _veiculos.postValue(response)
            } catch (e: Exception) {
                // Handle failure
            }
        }
    }
}
