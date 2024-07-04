package com.exemple.urbanbus.ui.stops

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exemple.urbanbus.data.models.BusStop
import com.exemple.urbanbus.data.models.BusStopLineArrival
import com.exemple.urbanbus.data.repositories.stops.BusStopRepository
import com.exemple.urbanbus.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StopsViewModel @Inject constructor(
    private val busStopRepository: BusStopRepository
) : ViewModel() {
    private val _stops = MutableLiveData<UiState<List<BusStop>>>()
    val stops: LiveData<UiState<List<BusStop>>> get() = _stops

    private val _lineArrival = MutableLiveData<UiState<List<BusStopLineArrival>>>()
    val lineArrival: LiveData<UiState<List<BusStopLineArrival>>> get() = _lineArrival

    fun getStops(searchTerm: String) = viewModelScope.launch {
        _stops.value = UiState.Loading
        busStopRepository.getBusStops(searchTerm) { stops ->
            _stops.value = stops
        }
    }

    fun getLineArrival(stopCode: Number) = viewModelScope.launch {
        _lineArrival.value = UiState.Loading
        busStopRepository.getStopArrival(stopCode) {
            _lineArrival.value = it
            Log.d("test", "$it")
        }
    }
}