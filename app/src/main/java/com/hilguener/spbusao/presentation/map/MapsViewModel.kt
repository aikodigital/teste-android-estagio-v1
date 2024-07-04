package com.hilguener.spbusao.presentation.map

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hilguener.spbusao.domain.model.Lines
import com.hilguener.spbusao.domain.model.Parades
import com.hilguener.spbusao.domain.model.PrevLine
import com.hilguener.spbusao.domain.model.Vehicles
import com.hilguener.spbusao.domain.usecase.BusManagerUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    private val isAuthenticate: StateFlow<Boolean> get() = _isAuthenticate


    fun authenticate(context: Context) {
        viewModelScope.launch {
            try {
                _isAuthenticate.value =
                    withContext(Dispatchers.IO) { manager.authenticate(context) }
                if (isAuthenticate.value) {
                    launch {
                        getParades("")
                    }
                }
            } catch (e: Exception) {
                haveError(e.message ?: "Unknown Error")
            }
        }
    }

    fun getPosVehiclesByLine(idLine: Int) {
        viewModelScope.launch {
            try {
                val resultList = withContext(Dispatchers.IO) {
                    manager.getPosVehiclesByLineUseCase(::haveError, idLine)
                }
                _listPosVehicles.value = resultList
                _isListPosVehiclesEmpty.value = resultList.isEmpty()
            } catch (e: Exception) {
                haveError(e.message ?: "Unknown Error")
            }
        }
    }

    fun getParades(term: String) {
        viewModelScope.launch {
            try {
                val resultList = withContext(Dispatchers.IO) {
                    manager.getParades(::haveError, term)
                }
                _listParades.value = resultList
                _isListParadesEmpty.value = resultList.isEmpty()
            } catch (e: Exception) {
                haveError(e.message ?: "Unknown Error")
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
            } catch (e: Exception) {
                haveError(e.message ?: "Unknown Error")
            }
        }
    }

    fun getLines(term: String) {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) { manager.getLines(::haveError, term) }
                _listLines.value = result
            } catch (e: Exception) {
                haveError(e.message ?: "Unknown Error")
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
    }
}

