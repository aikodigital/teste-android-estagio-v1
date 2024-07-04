package br.com.okayamafilho.testesptrans.ui.linhas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListaLinhasViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is linhas Fragment"
    }
    val text: LiveData<String> = _text
}