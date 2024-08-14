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
                    transactionMap = transactions
                },
                onFailure = {}
            )
        }
    }
}