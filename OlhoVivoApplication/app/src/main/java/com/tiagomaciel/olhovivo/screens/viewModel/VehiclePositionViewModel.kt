package com.tiagomaciel.olhovivo.screens.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tiagomaciel.olhovivo.api.ApiManager
import com.tiagomaciel.olhovivo.api.dataClass.StopLocation
import com.tiagomaciel.olhovivo.api.dataClass.VehicleLines
import com.tiagomaciel.olhovivo.api.dataClass.VehiclePosition

class VehiclePositionViewModel : ViewModel() {
    private val apiManager = ApiManager()

    private val _vehiclePosition = MutableLiveData<VehiclePosition?>()
    val vehiclePosition: LiveData<VehiclePosition?> get() = _vehiclePosition

    private val _vehicleLines = MutableLiveData<List<VehicleLines?>?>()
    val vehicleLines: LiveData<List<VehicleLines?>?> get() = _vehicleLines

    private val _vehicleStops = MutableLiveData<List<StopLocation?>?>()
    val vehicleStops: LiveData<List<StopLocation?>?> get() = _vehicleStops

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun authenticateAndFetchData(type: String, searchTerms: String) {
        apiManager.authenticateAndFetchData(
            onResultVehiclePosition = { vehiclePosition ->
                _vehiclePosition.postValue(vehiclePosition)
            },
            onResultVehicleLines = { vehicleLines ->
                _vehicleLines.postValue(vehicleLines)
            },
            onResultVehicleStops = { vehicleStops ->
                _vehicleStops.postValue(vehicleStops)
            },
            type = type,
            searchTerms = searchTerms
        )
    }

    private fun fetchVehiclePositions() {
        apiManager.fetchVehiclePositions { vehiclePosition ->
            if (vehiclePosition != null) {
                _vehiclePosition.postValue(vehiclePosition)
            } else {
                _error.postValue("Failed to fetch vehicle positions")
            }
        }
    }

    fun fetchBusLines(searchTerms: String) {
        apiManager.fetchBusLines(onResult = { vehicleLines ->
            if (vehicleLines != null) {
                _vehicleLines.postValue(vehicleLines)
            } else {
                _error.postValue("Failed to fetch bus lines")
            }
        }, searchTerms = searchTerms)
    }
}
