package com.example.aikodigital.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aikodigital.calls.getCorredores
import com.example.aikodigital.calls.getCorredoresParadas
import com.example.aikodigital.calls.getLinhas
import com.example.aikodigital.calls.getMapsApiRoute
import com.example.aikodigital.calls.getParadasPorLinhas
import com.example.aikodigital.calls.getPrevisaoChegada
import com.example.aikodigital.calls.getPrevisaoChegadaParada
import com.example.aikodigital.calls.getVeiculosPorLinhas
import com.example.aikodigital.service.Response.corredores.CorredoresParadasResponse
import com.example.aikodigital.service.Response.corredores.CorredoresResponse
import com.example.aikodigital.service.Response.linhas.LinhasResponse
import com.example.aikodigital.service.Response.maps.api_directions.MapsApiResponseList
import com.example.aikodigital.service.Response.paradas.ParadasResponse
import com.example.aikodigital.service.Response.previsao_chegada.PrevisaoChegadaResponseList
import com.example.aikodigital.service.Response.previsao_chegada.parada.PrevisaoChegadaParadaResponse
import com.example.aikodigital.service.Response.previsao_chegada.parada.PrevisaoChegadaParadaResponseList
import com.example.aikodigital.service.Response.veiculos.VeiculosResponseList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {
    private val _linhas = MutableStateFlow<List<LinhasResponse>>(emptyList())
    val linhas: StateFlow<List<LinhasResponse>> get() = _linhas

    private val _paradas = MutableStateFlow<List<ParadasResponse>>(emptyList())
    val paradas: StateFlow<List<ParadasResponse>> get() = _paradas

    private val _veiculos = MutableStateFlow(VeiculosResponseList("", emptyList()))
    val veiculos: MutableStateFlow<VeiculosResponseList> get() = _veiculos

    private val _previsao = MutableStateFlow(PrevisaoChegadaResponseList("", emptyList()))
    val previsao: MutableStateFlow<PrevisaoChegadaResponseList> get() = _previsao

    private val _previsaoParada = MutableStateFlow(PrevisaoChegadaParadaResponseList("", PrevisaoChegadaParadaResponse(0,"",0.0,0.0, emptyList())))
    val previsaoParada: MutableStateFlow<PrevisaoChegadaParadaResponseList> get() = _previsaoParada

    private val _corredores = MutableStateFlow<List<CorredoresResponse>>(emptyList())
    val corredores: StateFlow<List<CorredoresResponse>> get() = _corredores

    private val _corredoresParada = MutableStateFlow<List<CorredoresParadasResponse>>(emptyList())
    val corredoresParada: StateFlow<List<CorredoresParadasResponse>> get() = _corredoresParada

    private val _mapsRoute = MutableStateFlow(MapsApiResponseList(emptyList(), emptyList(),""))
    val mapsRoute: StateFlow<MapsApiResponseList> get() = _mapsRoute

    fun fetchLinhas(termoBusca: String) {
        viewModelScope.launch {
            try {
                val result = getLinhas(termoBusca)
                _linhas.value = result
            } catch (e: Exception) {
                _linhas.value = emptyList()
            }
        }
    }

    fun fetchParadas(codigoLinha: Int) {
        viewModelScope.launch {
            try {
                val result = getParadasPorLinhas(codigoLinha)
                _paradas.value = result
            } catch (e: Exception) {
                _paradas.value = emptyList()
            }
        }
    }

    fun fetchVeiculos(codigoLinha: Int) {
        viewModelScope.launch {
            try {
                val result = getVeiculosPorLinhas(codigoLinha)
                _veiculos.value = result
            } catch (e: Exception) {
                _veiculos.value = VeiculosResponseList("", emptyList())
            }
        }
    }

    fun fetchPrevisaoChegada(codigoLinha: Int) {
        viewModelScope.launch {
            try {
                val result = getPrevisaoChegada(codigoLinha)
                _previsao.value = result
            } catch (e: Exception) {
                _previsao.value = PrevisaoChegadaResponseList("", emptyList())
            }
        }
    }

    fun fetchPrevisaoChegadaParada(codigoParada: Int) {
        viewModelScope.launch {
            try {
                val result = getPrevisaoChegadaParada(codigoParada)
                _previsaoParada.value = result
            } catch (e: Exception) {
                _previsaoParada.value = PrevisaoChegadaParadaResponseList("", PrevisaoChegadaParadaResponse(0,"",0.0,0.0, emptyList()))
            }
        }
    }

    fun fetchCorredores() {
        viewModelScope.launch {
            try {
                val result= getCorredores()
                _corredores.value = result
            } catch (e: Exception) {
                _corredores.value = emptyList()
            }
        }
    }

    fun fetchCorredoresParadas(codigoCorredor: Int) {
        viewModelScope.launch {
            try {
                val result= getCorredoresParadas(codigoCorredor)
                _corredoresParada.value = result
            } catch (e: Exception) {
                _corredoresParada.value = emptyList()
            }
        }
    }

    fun fetchMapsRoute(origin: String, destination: String) {
        viewModelScope.launch {
            try {
                val result= getMapsApiRoute(origin,destination)
                _mapsRoute.value = result
            } catch (e: Exception) {
                _mapsRoute.value = MapsApiResponseList(emptyList(), emptyList(), "")
            }
        }
    }
}