package com.aiko.bus.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aiko.bus.models.Stop
import com.aiko.bus.repositories.StopRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StopViewModel : ViewModel() {

    private val _stopRepository = StopRepository()
    private val _stops = MutableStateFlow<List<Stop>>(emptyList())

    val stops: StateFlow<List<Stop>> = _stops

    fun getStopsByLine(lineId: Int) {
        viewModelScope.launch {
            _stops.value = _stopRepository.getStopsLine(lineId)
        }
    }

    fun getStopsByBusLane(busLaneId: Int) {
        viewModelScope.launch {
            _stops.value = _stopRepository.getStopsByBusLane(busLaneId)
        }
    }

}