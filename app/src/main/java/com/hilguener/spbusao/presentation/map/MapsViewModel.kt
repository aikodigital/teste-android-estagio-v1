package com.hilguener.spbusao.presentation.map

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hilguener.spbusao.domain.model.Lines
import com.hilguener.spbusao.domain.model.Parades
import com.hilguener.spbusao.domain.model.PosLines
import com.hilguener.spbusao.domain.model.PosVehicles
import com.hilguener.spbusao.domain.model.PrevLine
import com.hilguener.spbusao.domain.model.Vehicles
import com.hilguener.spbusao.domain.usecase.BusManagerUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapsViewModel(
    private val manager: BusManagerUseCase,
    app: Application,
) : AndroidViewModel(app) {
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    private val _listPosVehicles = MutableStateFlow<List<Vehicles>>(emptyList())
    val listPosVehicles: StateFlow<List<Vehicles>> get() = _listPosVehicles

    private var listPosVehiclesAndLine: PosVehicles? = null

    private val _listParades = MutableStateFlow<List<Parades>>(emptyList())
    val listParades: StateFlow<List<Parades>> get() = _listParades

    private val _listOfArrivalLines = MutableStateFlow<List<PrevLine>?>(null)
    val listOfArrivalLines: StateFlow<List<PrevLine>?> get() = _listOfArrivalLines

    private val _listLines = MutableStateFlow<List<Lines>?>(null)
    val listLines: StateFlow<List<Lines>?> get() = _listLines

    private val _isListPosVehiclesEmpty = MutableStateFlow(false)
    val isListPosVehiclesEmpty: StateFlow<Boolean> get() = _isListPosVehiclesEmpty

    private val _isListParadesEmpty = MutableStateFlow(false)
    val isListParadesEmpty: StateFlow<Boolean> get() = _isListParadesEmpty

    private val _isAuthenticate = MutableStateFlow(false)
    val isAuthenticate: StateFlow<Boolean> get() = _isAuthenticate

    val endLoading =
        combine(_listPosVehicles, _listParades) { vehicles, parades ->
            vehicles.isNotEmpty() && parades.isNotEmpty()
        }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun authenticate(context: Context) {
        viewModelScope.launch {
            try {
                _isAuthenticate.value = withContext(Dispatchers.IO) { manager.authenticate(context) }
                Log.d("MapsViewModel", "Authentication success: $_isAuthenticate.value")
            } catch (e: Exception) {
                haveError(e.message ?: "Unknown Error")
                Log.e("MapsViewModel", "Authentication error: ${e.message}", e)
            }
        }
    }

    fun getPosVehicles() {
        viewModelScope.launch {
            try {
                listPosVehiclesAndLine =
                    withContext(Dispatchers.IO) { manager.getPosVehicles(::haveError) }
                _listPosVehicles.value =
                    listPosVehiclesAndLine?.lines?.flatMap(PosLines::listOfVehicles) ?: emptyList()
            } catch (e: Exception) {
                haveError(e.message ?: "Unknown Error")
            }
        }
    }

    fun getPosVehiclesByLine(idLine: Int) {
        viewModelScope.launch {
            try {
                val resultList =
                    withContext(Dispatchers.IO) {
                        manager.getPosVehiclesByLineUseCase(
                            ::haveError,
                            idLine,
                        )
                    }

                if (resultList.isNotEmpty()) {
                    _listPosVehicles.value = resultList
                } else {
                    _isListPosVehiclesEmpty.value = true
                }
            } catch (e: Exception) {
                haveError(e.message ?: "Unknown Error")
            }
        }
    }

    fun getParades(term: String) {
        viewModelScope.launch {
            try {
                val resultList =
                    withContext(Dispatchers.IO) { manager.getParades(::haveError, term) }

                if (resultList.isNotEmpty()) {
                    _listParades.value = resultList
                } else {
                    _isListParadesEmpty.value = true
                }
            } catch (e: Exception) {
                haveError(e.message ?: "Unknown Error")
            }
        }
    }

    fun getParadesByLine(idLine: Int) {
        viewModelScope.launch {
            try {
                val resultList =
                    withContext(Dispatchers.IO) {
                        manager.getParadesByLineUseCase(
                            ::haveError,
                            idLine,
                        )
                    }

                if (resultList.isNotEmpty()) {
                    _listParades.value = resultList
                    Log.d("MapsViewModel", "Parades by line loaded: $resultList")
                } else {
                    _isListParadesEmpty.value = true
                    Log.d("MapsViewModel", "No Parades found for line: $idLine")
                }
            } catch (e: Exception) {
                haveError(e.message ?: "Unknown Error")
                Log.e("MapsViewModel", "Error loading Parades by line: ${e.message}", e)
            }
        }
    }

    fun getArrivalVehicles(id: Int) {
        viewModelScope.launch {
            try {
                _listOfArrivalLines.value =
                    withContext(Dispatchers.IO) {
                        manager.getPrevArrival(
                            ::haveError,
                            id,
                        )?.pointOfParade?.lines
                    }
                Log.d("MapsViewModel", "Arrival vehicles loaded: $_listOfArrivalLines.value")
            } catch (e: Exception) {
                haveError(e.message ?: "Unknown Error")
                Log.e("MapsViewModel", "Error loading Arrival vehicles: ${e.message}", e)
            }
        }
    }

    fun getLines(term: String) {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) { manager.getLines(::haveError, term) }
                _listLines.value = result
                Log.d("MapsViewModel", "Lines loaded: $result")
            } catch (e: Exception) {
                haveError(e.message ?: "Unknown Error")
                Log.e("MapsViewModel", "Error loading Lines: ${e.message}", e)
            }
        }
    }

    fun getSelectedParade(id: String) = _listParades.value.find { it.codeOfParade == id.toInt() }

    fun validateBusAndStopFields(
        busLine: String,
        busStopLocation: String,
        busStopLine: String,
    ): Boolean {
        return busLine.isNotBlank() && (busStopLocation.isNotBlank() || busStopLine.isNotBlank())
    }

    fun validateSearchFields(searchQuery: String): Boolean {
        return searchQuery.isNotEmpty()
    }

    private fun haveError(error: String) {
        _error.value = error
        Log.e("MapsViewModel", "Error occurred: $error")
    }
}
