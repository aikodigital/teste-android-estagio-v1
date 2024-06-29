package br.com.aiko.estagio.bussp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aiko.estagio.bussp.data.remote.response.Linha
import br.com.aiko.estagio.bussp.data.repository.TransRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: TransRepository
) : ViewModel() {

    private val _authentication = MutableLiveData<Boolean>()
    val authentication: MutableLiveData<Boolean> get() = _authentication

    private val _buscarlinha = MutableLiveData<List<Linha>>()
    val buscarLinha: MutableLiveData<List<Linha>> get() = _buscarlinha

    private val _buscarLinhaSentido = MutableLiveData<List<Linha>>()
    val buscarLinhaSentido: MutableLiveData<List<Linha>> get() = _buscarLinhaSentido

    fun authentication(token: String) {
        viewModelScope.launch {
            val result = repository.authentication(token)
            _authentication.value = result
        }
    }

    fun buscarLinha(termoBuscar: String) {
        viewModelScope.launch {
            val result = repository.buscarLinha(termoBuscar)
            _buscarlinha.value = result
        }
    }

    fun buscarLinhaSentido(termosBusca: String, sentido: Int) {
        viewModelScope.launch {
            val result = repository.buscarLinhaSentido(termosBusca, sentido)
            _buscarLinhaSentido.value = result
        }
    }

}