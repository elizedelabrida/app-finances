package com.elize.finances.extension

import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.*

fun BigDecimal.formatToBrazilianFormat(): String {
    val brazilianFormat = DecimalFormat.getCurrencyInstance(Locale("pt", "br"))
    return brazilianFormat.format(this).replace("-R$", "R$ -")
}