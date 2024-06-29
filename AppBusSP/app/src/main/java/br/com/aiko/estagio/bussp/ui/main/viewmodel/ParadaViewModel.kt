package br.com.aiko.estagio.bussp.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aiko.estagio.bussp.data.remote.response.Parada
import br.com.aiko.estagio.bussp.data.repository.TransRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParadasViewModel @Inject constructor(
    private val repository: TransRepository
) : ViewModel() {

    private val _paradas = MutableLiveData<List<Parada>>()
    val paradas: MutableLiveData<List<Parada>> get() = _paradas

    fun buscarParada(parada: String) {
        viewModelScope.launch {
            val result = repository.buscarParada(parada)
            _paradas.value = result
        }
    }

    fun buscarParadasPorLinha(codigoLinha: String) {
        viewModelScope.launch {
            val result = repository.buscarParadasPorLinha(codigoLinha)
            _paradas.value = result
        }
    }

    fun buscarParadasPorCorredor(codigoCorredor: Int) {
        viewModelScope.launch {
            val result = repository.buscarParadasPorCorredor(codigoCorredor)
            _paradas.value = result
        }
    }

}