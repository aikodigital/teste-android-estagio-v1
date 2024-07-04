package com.exemple.urbanbus.utils

// calcula a diferenca para mostrar o tempo que falta para o onibus chegar no card de previsao
fun calculateMinutesDifference(currentHour: String, arrivalHour: String): Int {
    val currentHourMinutes = convertToMinutes(currentHour)
    val arrivalHourMinutes = convertToMinutes(arrivalHour)
    val diffInMinutes = arrivalHourMinutes - currentHourMinutes

    return if (diffInMinutes <= 59) diffInMinutes else -1
}

fun convertToMinutes(time: String): Int {
    val hour = time.substring(0, 2).toInt()
    val minute = time.substring(3, 5).toInt()

    return hour * 60 + minute
}
