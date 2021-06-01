package com.elize.finances.extension

import java.text.SimpleDateFormat
import java.util.*


fun String.limitLength(charQuantity: Int): String {
    if (this.length > charQuantity) {
        val firstIndex = 0
        return "${this.substring(firstIndex, charQuantity)}..."
    }
    return this
}

fun String.convertBrazilianFormatToCalendar(): Calendar {
    val brazilianFormat = "dd/MM/yyyy"
    val formatter = SimpleDateFormat(brazilianFormat)
    val formattedDate = formatter.parse(this)
    val date = Calendar.getInstance()
    date.time = formattedDate
    return date
}