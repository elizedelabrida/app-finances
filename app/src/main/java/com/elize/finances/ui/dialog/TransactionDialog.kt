package com.elize.finances.ui.dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.elize.finances.R
import com.elize.finances.extension.convertBrazilianFormatToCalendar
import com.elize.finances.extension.formatToBrazilianFormat
import com.elize.finances.model.Transaction
import com.elize.finances.model.TransactionType
import kotlinx.android.synthetic.main.transaction.view.*
import java.math.BigDecimal
import java.util.*

abstract class TransactionDialog(private val context: Context,
                             private val viewGroup: ViewGroup) {
    private val createdView = createLayout()
    protected val valueEditText: EditText = createdView.transaction_value
    protected val dateEditText: EditText = createdView.transaction_date
    protected val spinnerCategory: Spinner = createdView.transaction_category
    protected abstract val titlePositiveButton : String

    private fun createLayout(): View {
        return LayoutInflater.from(context).inflate(
            R.layout.transaction,
            viewGroup, false
        )
    }

    fun call(transactionType: TransactionType, transactionDelegate: (transaction : Transaction) -> Unit) {
        configureDateField()
        configureCategoryField(transactionType)
        configureTransactionDialog(transactionDelegate, transactionType)
    }

    private fun configureTransactionDialog(
        delegate: (transaction: Transaction) -> Unit,
        transactionType: TransactionType
    ) {
        val title = getTitleBy(transactionType)
        AlertDialog.Builder(context)
            .setTitle(title)
            .setView(createdView)
            .setPositiveButton(titlePositiveButton, DialogInterface.OnClickListener { _, _ ->
                val valueText = valueEditText.text.toString()
                val value = convertValueToBigDecimal(valueText)
                val dateText = dateEditText.text.toString()
                val date = dateText.convertBrazilianFormatToCalendar()
                val categoryText = spinnerCategory.selectedItem.toString()
                val createdTransaction = Transaction(
                    value,
                    categoryText,
                    transactionType,
                    date
                )
                delegate(createdTransaction)
            })
            .setNegativeButton("Cancel", null)
            .show()
    }

    protected abstract fun getTitleBy(transactionType: TransactionType): Int

    private fun convertValueToBigDecimal(valueText: String): BigDecimal {
        return try {
            BigDecimal(valueText)
        } catch (exception: NumberFormatException) {
            Toast
                .makeText(context, "Not able to convert value", Toast.LENGTH_LONG)
                .show()
            BigDecimal.ZERO
        }
    }

    private fun configureCategoryField(transactionType: TransactionType) {
        val categories = getCategoriesBy(transactionType)
        val adapter = ArrayAdapter
            .createFromResource(
                context, categories,
                android.R.layout.simple_spinner_dropdown_item
            )
        spinnerCategory.adapter = adapter
    }

    protected fun getCategoriesBy(transactionType: TransactionType): Int {
        if (transactionType == TransactionType.REVENUE) {
            return R.array.revenue_categories
        }
        return R.array.expense_categories

    }

    private fun configureDateField() {
        val todayDate = Calendar.getInstance()
        val year = todayDate.get(Calendar.YEAR)
        val month = todayDate.get(Calendar.MONTH)
        val day = todayDate.get(Calendar.DAY_OF_MONTH)
        dateEditText.setText(todayDate.formatToBrazilianFormat())
        dateEditText.setOnClickListener {
            DatePickerDialog(
                context,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, month, dayOfMonth)
                    dateEditText.setText(selectedDate.formatToBrazilianFormat())
                },
                year, month, day
            ).show()
        }
    }
}