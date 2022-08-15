package com.martini.spnoponto.constants

import com.martini.spnoponto.BuildConfig

object Constants {
    const val apiBaseUrl = "https://aiko-olhovivo-proxy.aikodigital.io/"
    const val lineSearchUrl = "Linha/Buscar"
    const val authorizeUrl = "Login/Autenticar"
    const val linePositionUrl = "Posicao/Linha"
    const val busStopUrl = "Parada/Buscar?termosBusca=*"
    const val forecastUrl = "Previsao/Parada"

    const val defaultBusStopCode = 670010530

    const val apiKey = BuildConfig.O_KEY
    const val LOG_TAG = "LOG_TAG"

    const val sharedPrefsKey = "sharedPrefsKey"
    const val filterPrefsKey = "filterPrefsKey"

    const val goingToSecondaryTerminal = 1
    const val goingToPrimaryTerminal = 2

    const val saoPauloLatitude: Double = -23.540545
    const val saoPauloLongitude: Double = -46.643624
    const val saoPauloZ = 11.17f

    const val LINE_DETAILS_NAV_ARG = "LINE_DETAILS_NAV_ARG"
}