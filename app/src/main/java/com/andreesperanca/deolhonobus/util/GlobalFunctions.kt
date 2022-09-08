package com.andreesperanca.deolhonobus.util

import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.time.Instant
import java.time.ZoneId

fun snackBarCreator(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
}

fun dateStringFormatter(dateString: String): String {
    val timestamp = Instant.parse(dateString).atZone(ZoneId.of(("America/Sao_Paulo")))
    var minutesStamp = timestamp.minute.toString()

    if (minutesStamp.toInt() < 10) {
        minutesStamp = "0${timestamp.minute}"
    }
    return "${timestamp.hour}:${minutesStamp}"
}