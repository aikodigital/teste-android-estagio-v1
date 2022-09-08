package com.andreesperanca.deolhonobus.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreesperanca.deolhonobus.models.BusLine
import com.andreesperanca.deolhonobus.models.BusStop
import com.andreesperanca.deolhonobus.models.ForecastVehicleView
import com.andreesperanca.deolhonobus.repositories.BusStopDetailsRepository
import com.andreesperanca.deolhonobus.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BusStopDetailsViewModel(private val repository: BusStopDetailsRepository) : ViewModel() {

    private val _getForecast = MutableLiveData<Resource<List<ForecastVehicleView>>>()
    val getForecast: LiveData<Resource<List<ForecastVehicleView>>> = _getForecast

    private var _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite


    fun getForecastWithBusStopCode(busStopCode: String) {
        _getForecast.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.Main) {
            val fetchResult = repository.getForecastWithBusStopCode(busStopCode)
            _getForecast.postValue(fetchResult)
        }
    }


    fun favoriteVerify(busStop: BusStop) {
        viewModelScope.launch(Dispatchers.IO) {
            if(repository.favoriteBusStopVerify(busStop.idCodeBusStop) != null) {
                _isFavorite.postValue(true)
            } else {
                _isFavorite.postValue(false)
            }
        }
    }

    fun favoriteBusLine(busStop: BusStop) {
        viewModelScope.launch (Dispatchers.IO) {
            if (repository.favoriteBusStopVerify(busStop.idCodeBusStop) != null) {
                repository.deleteFavoriteBusLine(busStop.idCodeBusStop)
                _isFavorite.postValue(false)
            } else {
                repository.favoriteBusStop(busStop)
                _isFavorite.postValue(true)
            }
        }
    }
}