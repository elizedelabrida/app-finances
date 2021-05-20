package com.elize.finances.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import com.elize.finances.R
import com.elize.finances.extension.formatToBrazilianFormat
import com.elize.finances.extension.limitLength
import com.elize.finances.model.Transaction
import com.elize.finances.model.TransactionType
import kotlinx.android.synthetic.main.transaction_item.view.*

class TransactionListAdapter(
    private val transactions: List<Transaction>,
    private val context: Context
) : BaseAdapter() {
    private val categoryLimit = 14

    override fun getView(position: Int, view: View?, viewParent: ViewGroup?): View {
        val viewCreated =
            LayoutInflater.from(context).inflate(R.layout.transaction_item, viewParent, false)
        val transaction = transactions[position]
        addCategory(viewCreated, transaction)
        addDate(viewCreated, transaction)
        addValue(viewCreated, transaction)
        addIcon(transaction, viewCreated)
        return viewCreated
    }

    private fun addDate(
        viewCreated: View,
        transaction: Transaction
    ) {
        viewCreated.transaction_item_date.text = transaction.date.formatToBrazilianFormat()
    }

    private fun addCategory(
        viewCreated: View,
        transaction: Transaction
    ) {
        viewCreated.transaction_item_category.text = transaction.category.limitLength(categoryLimit)
    }

    private fun addIcon(
        transaction: Transaction,
        viewCreated: View
    ) {
        viewCreated.transaction_item_icon.setBackgroundResource(getIconByType(transaction.type))
    }

    private fun getIconByType(type: TransactionType): Int {
        if (type == TransactionType.REVENUE) {
            return R.drawable.icon_transaction_item_revenue
        }
        return R.drawable.icon_transaction_item_expense
    }

    private fun addValue(
        viewCreated: View,
        transaction: Transaction
    ) {
        val color: Int = getColorByType(transaction.type)
        viewCreated.transaction_item_value.setTextColor(color)
        viewCreated.transaction_item_value.text = transaction.value.formatToBrazilianFormat()
    }

    private fun getColorByType(type: TransactionType): Int {
        if (type == TransactionType.REVENUE) {
            return ContextCompat.getColor(context, R.color.revenue)
        }
        return ContextCompat.getColor(context, R.color.expense)
    }


    override fun getItem(position: Int): Transaction {
        return transactions[position]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return transactions.size
    }
}