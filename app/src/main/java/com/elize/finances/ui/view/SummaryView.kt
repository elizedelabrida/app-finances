package com.elize.finances.ui.view

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.elize.finances.R
import com.elize.finances.extension.formatToBrazilianFormat
import com.elize.finances.model.Summary
import com.elize.finances.model.Transaction
import kotlinx.android.synthetic.main.summary_card.view.*
import java.math.BigDecimal

class SummaryView(context: Context,
                  private val view: View,
                  transactions: List<Transaction>) {
    private val summary: Summary = Summary(transactions)
    private val revenueColor = ContextCompat.getColor(context, R.color.revenue)
    private val expenseColor = ContextCompat.getColor(context, R.color.expense)

    fun update() {
        addRevenue()
        addExpense()
        addTotal()
    }

    private fun addRevenue() {
        val revenueTotal = summary.revenue
        with(view.summary_card_revenue) {
            setTextColor(revenueColor)
            text = revenueTotal
                .formatToBrazilianFormat()
        }

    }

    private fun addExpense() {
        val expenseTotal = summary.expense

        with(view.summary_card_expense) {
            setTextColor(expenseColor)
            text = expenseTotal
                .formatToBrazilianFormat()
        }


    }

    private fun addTotal() {
        val total = summary.total
        val color = getTransactionColor(total)
        with(view.summary_card_total) {
            setTextColor(color)
            text = total
                .formatToBrazilianFormat()
        }


    }

    private fun getTransactionColor(total: BigDecimal): Int {
        if (total >= BigDecimal.ZERO) {
            return revenueColor
        }
        return expenseColor

    }
}