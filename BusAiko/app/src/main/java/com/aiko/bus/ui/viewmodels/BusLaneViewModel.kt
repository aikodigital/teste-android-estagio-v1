package com.aiko.bus.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aiko.bus.models.BusLane
import com.aiko.bus.repositories.BusLaneRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BusLaneViewModel: ViewModel() {
    private val repository = BusLaneRepository()
    private val _busLanes = MutableStateFlow<List<BusLane>>(emptyList())

    val busLanes: StateFlow<List<BusLane>> = _busLanes

    init {
        loadBusLanes()
    }

    private fun loadBusLanes() {
        viewModelScope.launch {
            try {
                _busLanes.value = repository.getBusLanes()
            }catch (e: Exception) {
                // TODO: tratar erro
            }
        }
    }

}