package com.elize.finances.dao

import com.elize.finances.model.Transaction

class TransactionDAO {
    companion object { private val transactions: MutableList<Transaction>  = mutableListOf() }
    val transactions : List<Transaction> = Companion.transactions

    fun add(transaction : Transaction) {
        Companion.transactions.add(transaction)
    }
    fun update(transaction: Transaction, position: Int) {
        Companion.transactions[position] =  transaction
    }
    fun remove(position: Int) {
        Companion.transactions.removeAt(position)
    }
}