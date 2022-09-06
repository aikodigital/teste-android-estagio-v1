package com.conti.onibusspemtemporeal.ui.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.conti.onibusspemtemporeal.data.models.BusRoute
import com.conti.onibusspemtemporeal.data.models.BusWithLine
import com.conti.onibusspemtemporeal.data.models.ResponseAllBus
import com.conti.onibusspemtemporeal.domain.apiRepository.OlhoVivoApiRepository
import com.conti.onibusspemtemporeal.domain.roomRepository.RoomRepository
import com.conti.onibusspemtemporeal.util.Constants.START_BUS_ROUTE
import com.conti.onibusspemtemporeal.util.Constants.START_MESSAGE
import com.conti.onibusspemtemporeal.util.retrofitHandling.Resource
import com.google.android.gms.maps.model.LatLng
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


    private val _authenticate = MutableLiveData<Boolean>(false)

    private val _busRoute = MutableLiveData<Resource<List<BusRoute>>>()
    val busRoute: LiveData<Resource<List<BusRoute>>>
        get() = _busRoute

    private val _favoritesBusRoute = MutableLiveData<List<BusRoute>>()
    val favoritesBusRoute: LiveData<List<BusRoute>>
        get() = _favoritesBusRoute

    private val _currentBuses = MutableLiveData<Resource<ResponseAllBus>>()

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState>
        get() = _uiState.asStateFlow()


    init {

        authenticate()
        viewModelScope.launch {
            getFavoritesBusRoute()
        }
    }


    fun getBus() {
        viewModelScope.launch {

            _currentBuses.postValue(Resource.Loading())

            _uiState.update {
                it.copy(isLoading = true)
            }

            try {
                if (_authenticate.value!!) {
                    val response = apiRepository.getAllBus()
                    _currentBuses.postValue(handleBusResponse(response))
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

    private fun handleBusResponse(response: Response<ResponseAllBus>): Resource<ResponseAllBus> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->

                val currentListBusWithRoute: MutableList<BusWithLine> = mutableListOf()
                var quantityBus = 0

                resultResponse.lineRelation.forEach { busRouteWithBus ->

                    quantityBus += busRouteWithBus.amountBusFound

                    busRouteWithBus.buses.forEach { bus ->

                        val latLng = LatLng(bus.latBus, bus.longBus)

                        val busWithLine = BusWithLine(
                            busRouteWithBus.fullPlacard,
                            bus.prefixBus,
                            busRouteWithBus.originPlacard,
                            busRouteWithBus.destinyPlacard,
                            bus.acessibleBus,
                            resultResponse.hourGet,
                            latLng
                        )

                        currentListBusWithRoute.add(busWithLine)
                    }
                }

                _uiState.update {
                    it.copy(
                        currentBuses = currentListBusWithRoute,
                        isLoading = false,
                        quantityBusInThisRoute = quantityBus
                    )
                }

                return Resource.Success(resultResponse)
            }
        }
        _uiState.update {
            it.copy(message = "Não foi possivel carregar, tente novamente")
        }
        return Resource.Error(response.message())
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

    fun favoriteBusRoute(busRoute: BusRoute) {
        viewModelScope.launch {
            roomRepository.favoriteBusRoute(busRoute)
            _uiState.update {
                it.copy(message = "Linha: ${busRoute.firstNumbersPlacard}-${busRoute.secondPartPlacard}, salva nos favoritos com Sucesso!!")
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


    fun clearLineCode() {
        _uiState.update {
            it.copy(lineCod = "")
        }
    }

    fun getBusRouteSelected(fullPlacard: String) {

        _uiState.update {
            it.copy(lineCod = fullPlacard)
        }

        viewModelScope.launch {

            _currentBuses.postValue(Resource.Loading())

            _uiState.update {
                it.copy(isLoading = true)
            }

            try {
                if (_authenticate.value!!) {
                    val response = apiRepository.getAllBus()
                    _currentBuses.postValue(handleBusRoutersResponseAndFilter(response))
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
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

    private fun handleBusRoutersResponseAndFilter(response: Response<ResponseAllBus>): Resource<ResponseAllBus> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->

                for (line in resultResponse.lineRelation) {
                    if (line.fullPlacard == _uiState.value.lineCod) {

                        val currentListBusWithRoute: MutableList<BusWithLine> = mutableListOf()

                        line.buses.forEach { bus ->

                            val latLng = LatLng(bus.latBus, bus.longBus)

                            val busWithLine = BusWithLine(
                                line.fullPlacard,
                                bus.prefixBus,
                                line.originPlacard,
                                line.destinyPlacard,
                                bus.acessibleBus,
                                resultResponse.hourGet,
                                latLng
                            )

                            currentListBusWithRoute.add(busWithLine)
                        }


                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                currentBuses = currentListBusWithRoute,
                                quantityBusInThisRoute = line.amountBusFound
                            )
                        }
                        return Resource.Success(resultResponse)
                    }
                }
            }
        }
        return Resource.Error(response.message())
    }


}

data class UiState(
    var message: String = START_MESSAGE,
    var isLoading: Boolean = false,
    var clearItem: Boolean = false,
    var lineCod: String = "",
    var quantityBusInThisRoute: Int = START_BUS_ROUTE,
    var currentBuses: MutableList<BusWithLine> = mutableListOf()
)


