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

        //Por falta de tempo ainda não coloquei um refresh na autenticação por uma quantidade de tempo
        authenticate()

        viewModelScope.launch {
            getFavoritesBusRoute()
        }

    }


    /** Função para realizar requisição da posição de todos os ônibus, coloco [_uiState] como loading true, e realizo dentro do try e catch,
     * a requisição utilizando o repositorio da api, utilizo da classe [handleBusRoutersResponse] para manipular a resposta e atualizar ela para
     * a [_uiState], caso tenha algum erro no processo, atualizo a [_uiState] com a mensagem do erro */
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

    /** Função para manipular uma resposta de linha em relação aos ônibus, caso a resposta for de sucesso
     * crio uma variável para armazenar a linha e os ônibus em circulação delas, após armazernas todos os õnibus
     * atualizo o [_uiState] com essa lista de onibus, e para finalizar a função retorno sucesso, caso não tenha entrado no if
     * finalizo a função retornando erro */
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
                            latLng,
                            busRouteWithBus.lineCod
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


    /** Função para manipular a resposta de linhas de ônibus caso a resposta seja de sucesso
     *  retorna Resource.Succes, caso não tenha entrado no if, retornar erro */
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
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            try {
                _authenticate.value = apiRepository.postAuthenticate()
                _uiState.update {
                    it.copy(isLoading = false)
                }
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


    /** Função para salvar a linha, envio a [busRoute] para o room utilizando o repository do room, e atualizo o [_uiState]
     * com a mensagem que a linha foi salva */
    fun favoriteBusRoute(busRoute: BusRoute) {
        viewModelScope.launch {
            roomRepository.favoriteBusRoute(busRoute)
            _uiState.update {
                it.copy(message = "Linha: ${busRoute.firstNumbersPlacard}-${busRoute.secondPartPlacard}, salva nos favoritos com Sucesso!!")
            }
        }
    }


    /** Função para realizer a coleta de um fluxo de Flow do [roomRepository] com todas as linhas que estão salvas no favorito
     * no Room e passo esse valor para mutableLiveData [_favoritesBusRoute] */
    private suspend fun getFavoritesBusRoute() {
        roomRepository.getFavoritesBusRoutes().collect { busRouteList ->
            _favoritesBusRoute.value = busRouteList
        }
    }


    /** Função para limpar as mensagens do [_uiState]*/
    fun clearMessages() {
        _uiState.update {
            it.copy(message = "")
        }
    }


    /** Função para limpar o letreiro completo atual*/
    fun clearLineCode() {
        _uiState.update {
            it.copy(lineCod = "")
        }
    }


    /** Função para realizar a requisição de todos os ônibus que estão circulando e a filtragem dessa requisição,
     * abro um escopo de viewmodel e atualizo o [_uiState] para loading true, no try e catch antes de realizar a requisição
     * verifico se já estou logado, caso sim, a requisição é feita e enviada para classe [handleBusRoutersResponseAndFilter]
     * para fazer a manipulação da response filtrar e atualizar o [_uiState] com o novo valor de lista de onibus com relação a linha
     * caso seja pego algum throwable durante a execução atualizo o [_uiState] com mensagem de erro*/
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


    /** Função para manipular o resultado de um Response, caso [response] seja de sucesso, utilizo do for in na relação de linha e ônibus
     *  e verifico qual linha tem o letreiro completo igual ao letreiro atual da [_uiState], quando forem iguais,
     *  crio uma lista para armazernar todos os ônibus dessa linha, após armazenar todos eles, atualizo o [_uiState], com o valor da [curentListBusWithRoute]
     *  finalizo o if retornando Resourcer.Sucess, caso não tenha entrado no if retorno Resource.Error*/
    private fun handleBusRoutersResponseAndFilter(response: Response<ResponseAllBus>): Resource<ResponseAllBus> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->

                //Para saber se a linha que está sendo buscada, tem algum ônibus em circulação
                var lineSearchExist: Boolean = false

                for (line in resultResponse.lineRelation) {
                    if (line.fullPlacard == _uiState.value.lineCod) {

                        lineSearchExist = true

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
                                latLng,
                                line.lineCod
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

                if (!lineSearchExist) {
                    _uiState.update {
                        it.copy(message = "A linha procurada não tem ônibus em circulação no momento")
                    }
                }
            }
        }
        return Resource.Error(response.message())
    }

    //dar o zoom no onibus caso ja tenha um zoom passar para false
    fun zoomBus() {
        if (_uiState.value.zoomCurrentBuses) {
            _uiState.update {
                it.copy(zoomCurrentBuses = false)
            }
        } else {
            _uiState.update {
                it.copy(zoomCurrentBuses = true)
            }
        }
    }

    //Atualizar a posição atual do usuario, e passar o zoom no usuario para tru
    fun currentLocationUser(currentUserLocation: LatLng) {
        _uiState.update {
            it.copy(
                currentLocationUser = currentUserLocation,
                focusUser = true
            )
        }
    }

    //Atualizar o estado do zoom da camera na posição atual do usuario para false
    fun offFocusUser() {
        _uiState.update {
            it.copy(focusUser = false)
        }
    }


}

/** data Class com o estado para ser passado do viewModel para a Ui, para ser utilizada em um StateFlow */
data class UiState(
    var message: String = START_MESSAGE,
    var isLoading: Boolean = false,
    var clearItem: Boolean = false,
    var lineCod: String = "",
    var quantityBusInThisRoute: Int = START_BUS_ROUTE,
    var currentBuses: MutableList<BusWithLine> = mutableListOf(),
    val zoomCurrentBuses: Boolean = false,
    val currentLocationUser: LatLng = LatLng(0.0, 0.0),
    val focusUser: Boolean = false
)


