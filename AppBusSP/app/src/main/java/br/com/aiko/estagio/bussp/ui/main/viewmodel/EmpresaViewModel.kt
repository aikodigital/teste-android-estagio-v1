package br.com.aiko.estagio.bussp.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aiko.estagio.bussp.data.remote.response.Empresas
import br.com.aiko.estagio.bussp.data.repository.TransRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmpresaViewModel @Inject constructor(
    private val repository: TransRepository
): ViewModel() {

    private val _empresas = MutableLiveData<Empresas>()
    val empresas:MutableLiveData<Empresas> get() = _empresas

    fun empresas() {
        viewModelScope.launch {
            val result = repository.empresas()

            _empresas.value = result
        }
    }
}