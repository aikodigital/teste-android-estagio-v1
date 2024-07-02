package br.com.aiko.estagio.bussp.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aiko.estagio.bussp.data.repository.TransRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val repository: TransRepository
) : ViewModel() {

    private val _authentication = MutableLiveData<Boolean>()
    val authentication: MutableLiveData<Boolean> get() = _authentication

    fun authentication(token: String) {
        viewModelScope.launch {
            val result = repository.authentication(token)
            _authentication.value = result
        }
    }
}