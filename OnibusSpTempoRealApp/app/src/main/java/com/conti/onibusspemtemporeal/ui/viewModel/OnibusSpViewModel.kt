package com.conti.onibusspemtemporeal.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.conti.onibusspemtemporeal.data.models.BusRoute
import com.conti.onibusspemtemporeal.domain.apiRepository.OlhoVivoApiRepository
import com.conti.onibusspemtemporeal.util.retrofitHandling.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class OnibusSpViewModel @Inject constructor(
    private val apiRepository: OlhoVivoApiRepository
) : ViewModel() {


    private val _authenticate = MutableLiveData<Boolean>()//MutableLiveData<Resource<ResponseBusRoute>>()


    private val _routersBus = MutableLiveData<Resource<List<BusRoute>>>()
    val routersBus: LiveData<Resource<List<BusRoute>>>
        get() = _routersBus

    init {
        authenticate()

    }

    private fun authenticate() = viewModelScope.launch {
        _authenticate.value = apiRepository.postAuthenticate()

        if(_authenticate.value!!){
            getRoutersBus()
        }
    }


    fun getRoutersBus() = viewModelScope.launch {

        safeCallRouters()

    }

    private suspend fun safeCallRouters() {
        _routersBus.postValue(Resource.Loading())
        try {
            val response = apiRepository.getRoutes("SÃO")
            _routersBus.postValue(handleRoutersResponse(response))

        } catch (t: Throwable) {
            when (t) {
                is IOException -> _routersBus.postValue(Resource.Error("Falha na rede"))
                else -> {
                    _routersBus.postValue(Resource.Error("Erro de conversão : ${t.message}"))
                    Log.d("tag", "${t.message}")
                }
            }
        }

    }


    private fun handleRoutersResponse(response: Response<List<BusRoute>>): Resource<List<BusRoute>> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


}


