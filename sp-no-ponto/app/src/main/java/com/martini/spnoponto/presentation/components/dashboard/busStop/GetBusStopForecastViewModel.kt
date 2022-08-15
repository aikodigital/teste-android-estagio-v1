package com.martini.spnoponto.presentation.components.dashboard.busStop

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.spnoponto.constants.Constants
import com.martini.spnoponto.domain.entities.busStop.BusStop
import com.martini.spnoponto.domain.entities.busStopForecast.BusStopForecastParams
import com.martini.spnoponto.domain.usecases.GetBusStopForecastState
import com.martini.spnoponto.domain.usecases.GetBusStopForecastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.Duration
import javax.inject.Inject

@HiltViewModel
class GetBusStopForecastViewModel @Inject constructor(
    private val getBusStopForecastUseCase: GetBusStopForecastUseCase
) : ViewModel() {
    private val _state = mutableStateOf<GetBusStopForecastState>(GetBusStopForecastState.Initial)

    val state: State<GetBusStopForecastState> = _state

    private var busStop: BusStop? = null

    init {
        initializeMapDefaultMarker()
        updateAutomatically()
    }

    private fun updateAutomatically() {
        viewModelScope.launch {
            while (isActive) {
                delay(Duration.ofSeconds(30).toMillis())
                busStop?.let {
                    refresh(it)
                }
            }
        }
    }

    private fun refresh(busStop: BusStop) {
        getBusStopForecastUseCase.refresh(BusStopForecastParams(busStop))
            .flowOn(Dispatchers.IO)
            .onEach { _state.value = it }
            .launchIn(viewModelScope)
    }

    private fun initializeMapDefaultMarker() {
        //Inicia o map num ponto central da cidade
        //Só para referência
        val busStop = BusStop(
            Constants.defaultBusStopCode,
            "",
            "",
            0.1,
            0.1
        )
        this.busStop = busStop
        val params = BusStopForecastParams(busStop)
        invoke(params)
    }

    fun setStop(newStop: BusStop) {
        busStop = newStop
        invoke(BusStopForecastParams(newStop))
    }

    operator fun invoke(params: BusStopForecastParams) {
        getBusStopForecastUseCase(params)
            .flowOn(Dispatchers.IO)
            .onEach { _state.value = it }
            .launchIn(viewModelScope)
    }
}