package com.elize.finances.ui.dialog

import android.content.Context
import android.view.ViewGroup
import com.elize.finances.R
import com.elize.finances.extension.formatToBrazilianFormat
import com.elize.finances.model.Transaction
import com.elize.finances.model.TransactionType

class UpdateTransactionDialog(
    viewGroup : ViewGroup,
    private val context : Context) : TransactionDialog(context, viewGroup) {

    fun call(transaction: Transaction, transactionDelegate : (transaction : Transaction) -> Unit) {
        val transactionType = transaction.type
        super.call(transactionType, transactionDelegate)
        initializeFields(transaction)
    }

    private fun initializeFields(
        transaction: Transaction
    ) {
        initializeValue(transaction)
        initializeDate(transaction)
        initializeCategories(transaction)
    }

    private fun initializeCategories(
        transaction: Transaction
    ) {
        val transactionType = transaction.type
        val returnedCategories = context.resources.getStringArray(getCategoriesBy(transactionType))
        val positionCategory = returnedCategories.indexOf(transaction.category)
        spinnerCategory.setSelection(positionCategory, true)
    }

    private fun initializeDate(transaction: Transaction) {
        dateEditText.setText(transaction.date.formatToBrazilianFormat())
    }

    private fun initializeValue(transaction: Transaction) {
        valueEditText.setText(transaction.value.toString())
    }

    override val titlePositiveButton: String
        get() = "Update"

    override fun getTitleBy(transactionType: TransactionType): Int {
        if (transactionType == TransactionType.EXPENSE) {
            return R.string.update_expense
        }
        return R.string.update_revenue
    }
}