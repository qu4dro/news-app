package ru.orlovvv.peter.newsapp.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun String.formatDate(): String? {
    val pattern: String = "yyyy-MM-dd'T'HH:mm:ss"
    val outputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
    val inputFormat: DateFormat = SimpleDateFormat(pattern, Locale.ENGLISH)

    val inputText = "2012-11-17T00:00:00.000-05:00"
    val date: Date = inputFormat.parse(inputText)
    return outputFormat.format(date)

}
