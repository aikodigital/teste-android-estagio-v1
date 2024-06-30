package br.com.aiko.estagio.bussp.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aiko.estagio.bussp.data.remote.response.PrevisaoChegada
import br.com.aiko.estagio.bussp.data.remote.response.PrevisaoChegadaLinha
import br.com.aiko.estagio.bussp.data.repository.TransRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrevisaoViewModel @Inject constructor(
    private val repository: TransRepository
) : ViewModel() {

    private val _previsao = MutableLiveData<PrevisaoChegada>()
    val previsao: MutableLiveData<PrevisaoChegada> get() = _previsao

    private val _previsaoLinha = MutableLiveData<PrevisaoChegadaLinha>()
    val previsaoLinha: MutableLiveData<PrevisaoChegadaLinha> get() = _previsaoLinha

    fun previsao(codigoParada: Int, codigoLinha: Int) {
        viewModelScope.launch {
            val result = repository.previsao(codigoParada, codigoLinha)
            _previsao.value = result
        }
    }

    fun previsaoLinha(codigoLinha: Int) {
        viewModelScope.launch {
            val result = repository.previsaoLinha(codigoLinha)
            _previsaoLinha.value = result
        }
    }

    fun previsaoParada(codigoParada:Int){
        viewModelScope.launch {
            val result = repository.previsaoParada(codigoParada)
            _previsao.value = result
        }
    }
}