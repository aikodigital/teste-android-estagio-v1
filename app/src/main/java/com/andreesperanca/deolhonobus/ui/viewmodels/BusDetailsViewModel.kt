package com.andreesperanca.deolhonobus.ui.viewmodels

import androidx.lifecycle.*
import com.andreesperanca.deolhonobus.models.BusLine
import com.andreesperanca.deolhonobus.models.BusStop
import com.andreesperanca.deolhonobus.models.Place
import com.andreesperanca.deolhonobus.repositories.BusDetailsRepository
import com.andreesperanca.deolhonobus.util.Resource
import com.andreesperanca.deolhonobus.util.SingleEventLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BusDetailsViewModel(private val repository: BusDetailsRepository) : ViewModel() {

    private var _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    private var _searchBusStop = MutableLiveData<Resource<List<BusStop>>>()
    val searchBusStop: LiveData<Resource<List<BusStop>>> = _searchBusStop

    private var _fetchBusLinePosition = SingleEventLiveData<Resource<List<Place>>>()
    val fetchBusLinePosition: SingleEventLiveData<Resource<List<Place>>> = _fetchBusLinePosition

    fun getBusStopWithBusLineCode(busLineCode: String) {
        _searchBusStop.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.Main) {
            val searchResult = repository.getBusStopWithBusLineCode(busLineCode)
            _searchBusStop.postValue(searchResult)
        }
    }

    fun getBusLinePositionWithBusLineCode(busLineCode: String) {
        _fetchBusLinePosition.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.Main) {
            val fetchResult = repository.getBusPositionWithBusLineCode(busLineCode)
            _fetchBusLinePosition.postValue(fetchResult)
        }
    }

    fun favoriteVerify(busLine : BusLine) {
        viewModelScope.launch(Dispatchers.IO) {
            if(repository.favoriteVerify(busLine.idCode) != null) {
                _isFavorite.postValue(true)
            } else {
                _isFavorite.postValue(false)
            }
        }
    }

    fun favoriteBusLine(busLine: BusLine) {
        viewModelScope.launch (Dispatchers.IO) {
            if (repository.favoriteVerify(busLine.idCode) != null) {
                repository.deleteFavoriteBusLine(busLine.idCode)
                _isFavorite.postValue(false)
            } else {
                repository.favoriteBusLine(busLine)
                _isFavorite.postValue(true)
            }
        }
    }
}