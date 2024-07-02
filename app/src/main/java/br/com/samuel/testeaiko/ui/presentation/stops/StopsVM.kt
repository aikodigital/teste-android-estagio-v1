package br.com.samuel.testeaiko.ui.presentation.stops

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.samuel.testeaiko.core.application.strategies.SearchStopsStrategy
import br.com.samuel.testeaiko.core.application.usecases.AuthenticateAppUC
import br.com.samuel.testeaiko.core.application.usecases.GetForecastByStopUC
import br.com.samuel.testeaiko.core.application.usecases.SearchBusByLineUC
import br.com.samuel.testeaiko.core.domain.model.BusPosition
import br.com.samuel.testeaiko.core.domain.model.BusStop
import br.com.samuel.testeaiko.infra.strategies.SearchStopsByCorridorStrategy
import br.com.samuel.testeaiko.infra.strategies.SearchStopsByLineStrategy
import br.com.samuel.testeaiko.util.ResourceResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StopsVM @Inject constructor(
    private val authenticateApp: AuthenticateAppUC,
    private val searchStopsByLineStrategy: SearchStopsByLineStrategy,
    private val searchStopsByCorridorStrategy: SearchStopsByCorridorStrategy,
    private val searchBusesPositionsByLine: SearchBusByLineUC,
    private val getForecastsByStop: GetForecastByStopUC
) : ViewModel() {

    private val _stops = MutableLiveData<List<BusStop>>()
    val stops: LiveData<List<BusStop>> = _stops

    private val _busPositions = MutableLiveData<List<BusPosition>>()
    val busPositions: LiveData<List<BusPosition>> = _busPositions

    private val _forecastsResult = MutableLiveData<ForecastResult>()
    val forecastResult: LiveData<ForecastResult> = _forecastsResult

    private lateinit var searchStopsStrategy: SearchStopsStrategy

    var lineId: Int? = null
    var corridorId: Int? = null

    private var authenticationRetries = 0

    fun initialize() {
        if (lineId != null) {
            searchStopsStrategy = searchStopsByLineStrategy
        }

        if (corridorId != null) {
            searchStopsStrategy = searchStopsByCorridorStrategy
        }
    }

    fun search() {
        if (lineId != null) {
            searchStopsByLineCode(lineId!!)
            searchBusesByLine(lineId!!)
        }

        if (corridorId != null) {
            searchStopsByCorridorCode(corridorId!!)
        }
    }

    private fun searchStopsByLineCode(lineCode: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = searchStopsStrategy.getStops(lineCode)) {
                is ResourceResult.Error -> {
                    if (result.error == "UNAUTHORIZED") {
                        authenticationRetries++
                        if (authenticate() && authenticationRetries <= 1) {
                            search()
                        }
                    }
                }

                is ResourceResult.Success -> {
                    viewModelScope.launch(Dispatchers.Main) {
                        _stops.postValue(result.data.orEmpty())
                        result.data?.forEach { stop ->
                            viewModelScope.launch(Dispatchers.IO) {
                                getBusForecastByStop(stop.code)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun searchStopsByCorridorCode(corridorId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = searchStopsStrategy.getStops(corridorId)) {
                is ResourceResult.Error -> {
                    if (result.error == "UNAUTHORIZED") {
                        authenticationRetries++
                        if (authenticate() && authenticationRetries <= 1) {
                            search()
                        }
                    }
                }

                is ResourceResult.Success -> {
                    _stops.postValue(result.data.orEmpty())
                }
            }
        }
    }

    private fun searchBusesByLine(lineCode: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = searchBusesPositionsByLine(lineCode)) {
                is ResourceResult.Error -> {
                    if (result.error == "UNAUTHORIZED") {
                        authenticationRetries++
                        if (authenticate() && authenticationRetries <= 1) {
                            search()
                        }
                    }
                }

                is ResourceResult.Success -> {
                    viewModelScope.launch(Dispatchers.Main) {
                        _busPositions.postValue(result.data.orEmpty())
                    }
                }
            }
        }
    }

    private fun getBusForecastByStop(stopCode: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getForecastsByStop(stopCode)) {
                is ResourceResult.Error -> {
                    if (result.error == "UNAUTHORIZED") {
                        authenticationRetries++
                        if (authenticate() && authenticationRetries <= 1) {
                            search()
                        }
                    }
                }

                is ResourceResult.Success -> {
                    viewModelScope.launch(Dispatchers.Main) {
                        _forecastsResult.postValue(ForecastResult(stopCode, result.data.orEmpty()))
                    }
                }
            }
        }
    }

    private suspend fun authenticate(): Boolean {
        return when (authenticateApp()) {
            is ResourceResult.Error -> {
                false
            }

            is ResourceResult.Success -> {
                true
            }
        }
    }

}