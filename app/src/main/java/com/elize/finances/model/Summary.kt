package com.elize.finances.model

import java.math.BigDecimal

class Summary(private val transactions: List<Transaction>) {
    val revenue get() = sumBy(TransactionType.REVENUE)
    val expense get() = sumBy(TransactionType.EXPENSE)
    val total get() = revenue.subtract(expense)

    private fun sumBy(type: TransactionType): BigDecimal {
        val sumTransactionsByType = transactions
            .filter { it.type == type}
            .sumByDouble { it.value.toDouble() }
        return BigDecimal(sumTransactionsByType)
    }


}