package com.elize.finances.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.elize.finances.R
import com.elize.finances.model.Transaction
import com.elize.finances.model.TransactionType
import com.elize.finances.ui.adapter.TransactionListAdapter
import kotlinx.android.synthetic.main.activity_transaction_list.*
import java.math.BigDecimal

class TransactionListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_list)
        val transactions: List<Transaction> = mockedTransactionList()
        configureList(transactions)
    }

    private fun configureList(transactions: List<Transaction>) {
        transaction_list_listview.adapter = TransactionListAdapter(transactions, this)
    }

    private fun mockedTransactionList() = listOf(
        Transaction(
            value = BigDecimal(20.5),
            category = "Food",
            type = TransactionType.EXPENSE
        ),
        Transaction(
            value = BigDecimal(100),
            type = TransactionType.REVENUE
        )
    )
}