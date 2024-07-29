package com.aiko.bus.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aiko.bus.models.Vehicle
import com.aiko.bus.repositories.VehicleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VehiclesViewModel: ViewModel() {
    private val vehicleRepository = VehicleRepository()
    private val _vehicles = MutableStateFlow<List<Vehicle>>(emptyList())
    private val _isLoading = MutableStateFlow(false)

    val isLoading: StateFlow<Boolean> = _isLoading
    val vehicles: StateFlow<List<Vehicle>> = _vehicles

    fun getVehicles(lineId: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _vehicles.value = vehicleRepository.getPositionsByLine(lineId)
            } catch (e: Exception) {
                // TODO: implementar tratamento de erro
            } finally {
                _isLoading.value = false
            }
        }
    }

}
