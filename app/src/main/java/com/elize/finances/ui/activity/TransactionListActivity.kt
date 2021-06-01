package com.elize.finances.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.elize.finances.R
import com.elize.finances.dao.TransactionDAO
import com.elize.finances.model.Transaction
import com.elize.finances.model.TransactionType
import com.elize.finances.ui.adapter.TransactionListAdapter
import com.elize.finances.ui.dialog.AddTransactionDialog
import com.elize.finances.ui.dialog.UpdateTransactionDialog
import com.elize.finances.ui.view.SummaryView
import kotlinx.android.synthetic.main.activity_transaction_list.*

class TransactionListActivity : AppCompatActivity() {
    private val transactionDAO : TransactionDAO = TransactionDAO()
    private val transactions : List<Transaction> = transactionDAO.transactions
    private val viewActivity : View by lazy { window.decorView }
    private val viewGroupActivity by lazy { viewActivity as ViewGroup }
    private val removeMenuId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_list)
        configureSummary()
        configureList()
        configureButtonsFromMenu()
    }

    private fun configureButtonsFromMenu() {
        list_transaction_add_revenue.setOnClickListener {
            callAddTransactionDialog(TransactionType.REVENUE)
        }

        list_transaction_add_expense.setOnClickListener {
            callAddTransactionDialog(TransactionType.EXPENSE)
        }
    }

    private fun callAddTransactionDialog(transactionType: TransactionType) {
        AddTransactionDialog(viewGroupActivity, this)
            .call(transactionType, transactionDelegate = { createdTransaction ->
                addTransaction(createdTransaction)
                list_transaction_add_menu.close(true)
            })
    }

    private fun addTransaction(transaction: Transaction) {
        transactionDAO.add(transaction)
        updateTransactions()
    }


    private fun updateTransactions() {
        configureList()
        configureSummary()
    }

    private fun configureSummary() {
        val summaryView = SummaryView(this, viewActivity, transactions)
        summaryView.update()
    }

    private fun configureList() {
        val listTransactionAdapter = TransactionListAdapter(transactions, this)
        with (transaction_list_listview) {
            adapter = listTransactionAdapter
            setOnItemClickListener { _, _, position, _ ->
                val transaction = transactions[position]
                callUpdateTransactionDialog(transaction, position)
            }
            setOnCreateContextMenuListener { menu, _, _ ->
                menu.add(Menu.NONE, removeMenuId, Menu.NONE, "Remove")
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (item.itemId == removeMenuId) {
            val itemPosition = getSelectedItemPosition(item)
            removeTransaction(itemPosition)
        }
        return super.onContextItemSelected(item)
    }

    private fun getSelectedItemPosition(item: MenuItem): Int {
        val adapterMenuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return adapterMenuInfo.position
    }

    private fun removeTransaction(itemPosition: Int) {
        transactionDAO.remove(itemPosition)
        updateTransactions()
    }

    private fun callUpdateTransactionDialog(
        transaction: Transaction,
        position: Int
    ) {
        UpdateTransactionDialog(viewGroupActivity, this)
            .call(transaction, transactionDelegate = { updatedTransaction ->
                updateTransaction(position, updatedTransaction)
            })
    }

    private fun updateTransaction(
        position: Int,
        transaction: Transaction
    ) {
        transactionDAO.update(transaction, position)
        updateTransactions()
    }
}