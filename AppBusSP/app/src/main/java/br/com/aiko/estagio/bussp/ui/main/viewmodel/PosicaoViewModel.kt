package br.com.aiko.estagio.bussp.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aiko.estagio.bussp.data.remote.response.PosVeiculo
import br.com.aiko.estagio.bussp.data.remote.response.Posicao
import br.com.aiko.estagio.bussp.data.repository.TransRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PosicaoViewModel @Inject constructor(
    private val repository: TransRepository
) : ViewModel() {

    private val _posicao = MutableLiveData<Posicao>()
    val posicao: MutableLiveData<Posicao> get() = _posicao

    private val _posicaoLinha = MutableLiveData<PosVeiculo>()
    val posicaoLinha: MutableLiveData<PosVeiculo> get() = _posicaoLinha

    fun posicao() {
        viewModelScope.launch {
            val result = repository.posicao()
            _posicao.value = result
        }
    }

    fun posicaoLinha(codigoLinha: Int) {
        viewModelScope.launch {
            val result = repository.posicaoLinha(codigoLinha)
            _posicaoLinha.value = result
        }
    }

    fun posicaoGaragem(codigoEmpresa: Int, codigoLinha: Int) {
        viewModelScope.launch {
            val result = repository.posicaoGaragem(codigoEmpresa, codigoLinha)
            _posicao.value = result
        }
    }
}