package com.example.financemanagement.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financemanagement.model.Transactions
import com.example.financemanagement.repository.ReasonRepository
import com.example.financemanagement.repository.TransactionRepository
import kotlinx.coroutines.launch
import java.time.Instant.ofEpochMilli
import java.time.LocalDate
import java.time.ZoneId

class TransactionsViewModel : ViewModel() {

    var transactionMap by mutableStateOf(mapOf<String, Transactions>())
        private set

    var reasonsMap by mutableStateOf(mapOf<String, String>())
        private set

    init {
        fetchTransactions()
        fetchReasons()
    }

    private fun fetchReasons() {
        viewModelScope.launch {
            ReasonRepository.getReasons(
                onChange = { reasons ->
                    reasonsMap = reasons
                },
                onFailure = {}
            )
        }
    }

    //Change it later so that repository filters the month and not the viewmodel
    private fun fetchTransactions() {
        viewModelScope.launch {
            TransactionRepository.listenTransactions(
                onChange = { transactions ->
                    // Filter transactions by current month
                    val currentMonthTransactions = transactions.filter {
                        val localDate = ofEpochMilli(it.value.date)
                            .atZone(ZoneId.systemDefault()).toLocalDate()
                        localDate.year == LocalDate.now().year && localDate.month == LocalDate.now().month
                    }
                    transactionMap = currentMonthTransactions.toSortedMap { o1, o2 ->
                        transactions[o2]!!.date.compareTo(transactions[o1]!!.date)
                    }
                },
                onFailure = {}
            )
        }
    }
}