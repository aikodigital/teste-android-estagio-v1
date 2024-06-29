package br.com.aiko.estagio.bussp.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aiko.estagio.bussp.data.remote.response.Corredor
import br.com.aiko.estagio.bussp.data.repository.TransRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CorredorViewModel @Inject constructor(
    private val repository: TransRepository
) : ViewModel() {

    private val _corredores = MutableLiveData<List<Corredor>>()
    val corredor: MutableLiveData<List<Corredor>> get() = _corredores

    fun corredor() {
        viewModelScope.launch {
            val result = repository.corredor()
            _corredores.value = result
        }
    }
}