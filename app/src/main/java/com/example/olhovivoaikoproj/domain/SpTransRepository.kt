package com.example.olhovivoaikoproj.domain

import com.example.olhovivoaikoproj.data.request.SpTransApi
import com.example.olhovivoaikoproj.data.response.Linha
import com.example.olhovivoaikoproj.data.response.Paradas
import com.example.olhovivoaikoproj.data.response.Posicao
import com.example.olhovivoaikoproj.data.response.PrevisaoParadaObj

class SpTransRepository (private val spTransApi: SpTransApi) {

    suspend fun getAuthenticateToken(): Boolean {
        return spTransApi.authenticateToken("47bc3fbe804a7666f8c5bc51a5c67d7dafed485c63c8b344b7bf258827d44cf1")
    }

    suspend fun getLinhas(termosBusca: String): List<Linha> {
        return spTransApi.getLinhas(termosBusca)
    }

    suspend fun getParadas(termosBusca: String): List<Paradas>{
        return spTransApi.getParadas(termosBusca)
    }

    suspend fun getPosicaoLinha(codigoLinha: String): List<Posicao>{
        return spTransApi.getPosicaoLinha(codigoLinha)
    }

    suspend fun getPrevisao(codigoParada: String): PrevisaoParadaObj{
        return spTransApi.getPrevisao(codigoParada)
    }
}