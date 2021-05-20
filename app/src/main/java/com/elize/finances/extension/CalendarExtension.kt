package com.elize.finances.extension

import java.text.SimpleDateFormat
import java.util.*

fun Calendar.formatToBrazilianFormat(): String {
    val brazilianFormat = "dd/MM/yyyy"
    val formatter = SimpleDateFormat(brazilianFormat)
    return formatter.format(this.time)
}