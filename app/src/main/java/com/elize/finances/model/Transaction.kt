package com.elize.finances.model

import java.math.BigDecimal
import java.util.Calendar

class Transaction(
    val value: BigDecimal,
    val category: String = "Undefined",
    val type: TransactionType,
    val date: Calendar = Calendar.getInstance()
)