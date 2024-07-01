package br.com.aj.message.appaiko.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.aj.message.appaiko.data.PrevisaoParada
import br.com.aj.message.appaiko.repository.http.HttpRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PrevFragmentViewModel(private val repo: HttpRepository) : ViewModel() {

    val prevParadas = MutableLiveData<PrevisaoParada>()


    fun getprevBusParada(id:String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val value = repo.previsionBusParada(id)
                prevParadas.value = value
            } catch (e: Exception) {

            }
        }
    }
}