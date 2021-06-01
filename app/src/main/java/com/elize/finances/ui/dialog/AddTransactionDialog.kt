package com.elize.finances.ui.dialog

import android.content.Context
import android.view.ViewGroup
import com.elize.finances.R
import com.elize.finances.model.TransactionType

class AddTransactionDialog(
    viewGroup : ViewGroup,
    context : Context) : TransactionDialog(context, viewGroup) {
    override val titlePositiveButton: String
        get() = "Add"

    override fun getTitleBy(transactionType: TransactionType): Int {
        if (transactionType == TransactionType.EXPENSE) {
            return R.string.add_expense
        }
        return R.string.add_revenue
    }
}