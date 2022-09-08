package com.example.olhovivoaikoproj.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.olhovivoaikoproj.data.response.Linha
import com.example.olhovivoaikoproj.data.response.Paradas
import com.example.olhovivoaikoproj.data.response.Posicao
import com.example.olhovivoaikoproj.data.response.PrevisaoParadaObj
import com.example.olhovivoaikoproj.domain.SpTransRepository
import kotlinx.coroutines.launch

class MainViewModel(private val spTransRepository: SpTransRepository): ViewModel() {

    fun getAuthenticateToken(onSucess: (Boolean) -> Unit, onFailure: (Throwable) -> Unit) {
        viewModelScope.launch {
            runCatching {
                 spTransRepository.getAuthenticateToken()
            }.onSuccess {
                onSucess.invoke(it)
            }.onFailure {
                onFailure.invoke(it)
            }
        }
    }

    fun getLinhas(termosBuscas: String, onSucess: (List<Linha>) -> Unit, onFailure: (Throwable) -> Unit) {
        viewModelScope.launch {
            runCatching {
                spTransRepository.getLinhas(termosBuscas)
            }.onSuccess {
                onSucess.invoke(it)
            }.onFailure {
                onFailure.invoke(it)
            }
        }
    }

    fun getParadas (termosBuscas: String, onSucess: (List<Paradas>) -> Unit, onFailure: (Throwable) -> Unit){
        viewModelScope.launch {
            runCatching {
                spTransRepository.getParadas(termosBuscas)
            }.onSuccess {
                onSucess.invoke(it)
            }.onFailure {
                onFailure.invoke(it)
            }
        }
    }

    fun getPosicaoLinha(codigoLinha: String, onSucess: (List<Posicao>) -> Unit, onFailure: (Throwable) -> Unit){
        viewModelScope.launch {
            runCatching {
                spTransRepository.getPosicaoLinha(codigoLinha)
            }.onSuccess {
                onSucess.invoke(it)
            }.onFailure {
                onFailure.invoke(it)
            }
        }
    }

    fun getPrevisao(codigoParada: String, onSucess: (PrevisaoParadaObj) -> Unit, onFailure: (Throwable) -> Unit){
        viewModelScope.launch {
            runCatching {
                spTransRepository.getPrevisao(codigoParada)
            }.onSuccess {
                onSucess.invoke(it)
            }.onFailure {
                onFailure.invoke(it)
            }
        }
    }

}