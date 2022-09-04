package com.conti.onibusspemtemporeal.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.conti.onibusspemtemporeal.data.models.BusRoute
import com.conti.onibusspemtemporeal.domain.apiRepository.OlhoVivoApiRepository
import com.conti.onibusspemtemporeal.domain.roomRepository.RoomRepository
import com.conti.onibusspemtemporeal.util.retrofitHandling.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class OnibusSpViewModel @Inject constructor(
    private val apiRepository: OlhoVivoApiRepository,
    private val roomRepository: RoomRepository
) : ViewModel() {


    private val _authenticate = MutableLiveData<Boolean>()

    private val _busRoute = MutableLiveData<Resource<List<BusRoute>>>()
    val busRoute: LiveData<Resource<List<BusRoute>>>
        get() = _busRoute

    private val _favoritesBusRoute = MutableLiveData<List<BusRoute>>()
    val favoritesBusRoute: LiveData<List<BusRoute>>
        get() = _favoritesBusRoute


    private val _uiState: MutableStateFlow<UiStateBusRoute> = MutableStateFlow(UiStateBusRoute())
    val uiState: StateFlow<UiStateBusRoute>
        get() = _uiState.asStateFlow()


    init {

        authenticate()

        viewModelScope.launch {
            getFavoritesBusRoute()
        }
    }


    /** Função para realizar uma requisição de GET na [apiRepository], consultar as linhas de onibus
     * disponiveis de acordo com o que foi digitado pelo usuário e armazenado na, [busRoute],
     * ao começar a função [_busRoute] recebe estado de Loading, e em um try e catch realizo a requisição e atualizo [_busRoute] com o resultado da
     * lista de linhas disponiveis ou caso não tenha disponivel ou ocorra algum problema na requisição,
     * o cath retorna a mensagem para atualizar [uiState]*/
    fun searchBusRoute(busRoute: String) {
        viewModelScope.launch {
            _busRoute.postValue(Resource.Loading())
            try {
                if (_authenticate.value!!) {
                    val response = apiRepository.getRoutes(busRoute)
                    _busRoute.postValue(handleBusRoutersResponse(response))
                } else {
                    _uiState.update {
                        it.copy(message = "Verfique sua conexão, o aplicativo não funciona sem internet ")
                    }
                }
            } catch (t: Throwable) {
                _uiState.update {
                    it.copy(message = t.message.toString())
                }
            }
        }
    }

    private fun handleBusRoutersResponse(response: Response<List<BusRoute>>): Resource<List<BusRoute>> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    /** Função para realizar a autenticação da API OLHO VIVO
     * no try e catch realizo a requisição e caso a api seja autenticada com sucesso
     * armazeno a resposta em [_authenticate] e caso ocorra algum problema
     * atualizo a [_uiState] com a mensagem de erro dependendo do tipo de exception*/
    private fun authenticate() {
        viewModelScope.launch {
            try {
                _authenticate.value = apiRepository.postAuthenticate()
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> {
                        _uiState.update {
                            it.copy(message = t.message.toString())
                        }
                    }
                    else -> {
                        _uiState.update {
                            it.copy(message = "Erro de conversão")
                        }
                    }
                }
            }
        }
    }

    fun selectTheBusRoute(lineCod: Int) {
        _uiState.update {
            it.copy(lineCod = lineCod)
        }
    }

    fun favoriteBusRoute(busRoute: BusRoute) {
        viewModelScope.launch {
            roomRepository.favoriteBusRoute(busRoute)
            _uiState.update {
                it.copy(message = "Linha: ${busRoute.lineCod}, salva nos favoritos com Sucesso!!")
            }
        }
    }

    private suspend fun getFavoritesBusRoute() {
        roomRepository.getFavoritesBusRoutes().collect { busRouteList ->
            _favoritesBusRoute.value = busRouteList
        }
    }

    fun clearMessages() {
        _uiState.update {
            it.copy(message = "")
        }
    }


}

data class UiStateBusRoute(
    var message: String = "",
    var lineCod: Int = -1,
    var quantityBusInThisRoute: Int = 0
)


