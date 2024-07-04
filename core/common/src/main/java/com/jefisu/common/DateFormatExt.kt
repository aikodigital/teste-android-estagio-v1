package com.jefisu.common

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val DATE_PATTERN_API = "yyyy-MM-dd'T'HH:mm:ss'Z'"

fun formatDateToHour(
    dateString: String,
    inputPattern: String = DATE_PATTERN_API,
    outputPattern: String = "HH'h'mm"
): String {
    val inputFormat = SimpleDateFormat(inputPattern, Locale.getDefault())
    val outputFormat = SimpleDateFormat(outputPattern, Locale.getDefault())
    val date: Date = inputFormat.parse(dateString) ?: return "Invalid date format"
    return outputFormat.format(date)
}