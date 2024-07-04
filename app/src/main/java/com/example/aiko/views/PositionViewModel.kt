package com.example.aiko.views

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.aiko.data.model.Position
import com.example.aiko.data.model.StopBusItem
import com.example.aiko.data.remote.ApiClient
import com.example.aiko.data.remote.ApiService
import com.example.aiko.data.repository.PositionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PositionViewModel() : ViewModel() {

    private val repository = PositionRepository()

    private val _position = MutableLiveData<Position>()
    val position: MutableLiveData<Position> = _position

    private val _stopBus = MutableLiveData<List<StopBusItem>>()
    val stopBus: MutableLiveData<List<StopBusItem>> = _stopBus

    private val _auth = MutableStateFlow<Boolean>(false)
    val auth: StateFlow<Boolean> = _auth


    fun fetchAuth() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.postAuth()
            _auth.value = response
        }
    }
    fun fetchPosition() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getPosition()
            _position.postValue(response)
        }
    }

    fun fetchStopBus() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getStopBus()
            _stopBus.postValue(response)
        }
    }
}
