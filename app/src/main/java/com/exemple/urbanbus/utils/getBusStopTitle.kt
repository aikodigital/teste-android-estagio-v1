package com.exemple.urbanbus.utils

import com.exemple.urbanbus.data.models.BusStop

// verifica se o parametro name esta preenchido para mostrar no title da toolbar
fun getBusStopTitle(busStop: BusStop): String {
    return busStop.name.ifBlank {
        busStop.code.toString()
    }
}