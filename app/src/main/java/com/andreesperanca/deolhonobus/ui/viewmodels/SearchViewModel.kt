package com.andreesperanca.deolhonobus.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreesperanca.deolhonobus.models.BusLine
import com.andreesperanca.deolhonobus.models.BusStop
import com.andreesperanca.deolhonobus.repositories.SearchRepository
import com.andreesperanca.deolhonobus.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: SearchRepository) : ViewModel() {

    private val _fetchBusLineWithDenominationOrName = MutableLiveData<Resource<List<BusLine>>>()
    val fetchBusLineWithDenominationOrName: LiveData<Resource<List<BusLine>>> =
        _fetchBusLineWithDenominationOrName

    private val _authResult = MutableLiveData<Resource<String>>()
    val authResult: LiveData<Resource<String>> = _authResult

    private val _fetchBusStopWithNameOrAddress = MutableLiveData<Resource<List<BusStop>>>()
    val fetchBusStopWithNameOrAddress: LiveData<Resource<List<BusStop>>> =
        _fetchBusStopWithNameOrAddress

    private val _fetchBusStopWithHallCode = MutableLiveData<Resource<List<BusStop>>>()
    val fetchBusStopWithHallCode: LiveData<Resource<List<BusStop>>> = _fetchBusStopWithHallCode

    private val _fetchBusStopWithBusLineCode = MutableLiveData<Resource<List<BusStop>>>()
    val fetchBusStopWithBusLineCode: LiveData<Resource<List<BusStop>>> =
        _fetchBusStopWithBusLineCode

    fun getAuthInApi() = viewModelScope.launch(Dispatchers.Main) {
        val resultAuth = repository.getAuthInApi()
        _authResult.postValue(resultAuth)
    }

    fun getBusLineWithDenominationOrNumber(searchTerms: String) {
        _fetchBusLineWithDenominationOrName.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.Main) {
            val fetchResult = repository.getBusLines(searchTerms)
            _fetchBusLineWithDenominationOrName.postValue(fetchResult)
        }
    }

    fun getBusStopWithAddressOrName(searchTerms: String) {
        _fetchBusStopWithNameOrAddress.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.Main) {
            val fetchResult = repository.getBusStopWithAddressOrName(searchTerms)
            _fetchBusStopWithNameOrAddress.postValue(fetchResult)
        }
    }

    fun getBusStopWithHallCode(hallCode: String) {
        _fetchBusStopWithHallCode.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.Main) {
            val fetchResult = repository.getBusStopWithHallCode(hallCode)
            _fetchBusStopWithHallCode.postValue(fetchResult)
        }
    }

    fun getBusStopWithBusLineCode(searchTerms: String) {
        _fetchBusStopWithBusLineCode.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val fetchResult = repository.getBusStopWithBusLineCode(searchTerms)
            _fetchBusStopWithBusLineCode.postValue(fetchResult)
        }
    }
}