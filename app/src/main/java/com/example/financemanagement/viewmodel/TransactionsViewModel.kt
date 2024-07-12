package com.example.financemanagement.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financemanagement.model.Transactions
import com.example.financemanagement.repository.TransactionRepository
import kotlinx.coroutines.launch

class TransactionsViewModel : ViewModel() {

    var transactionMap by mutableStateOf(mapOf<String, Transactions>())
        private set

    init {
        fetchTransactions()
    }

    private fun fetchTransactions() {
        viewModelScope.launch {
            TransactionRepository.getTransactions(
                onChange = { transactions ->
                    transactionMap = transactions.toSortedMap { o1, o2 ->
                        transactions[o2]!!.date.compareTo(transactions[o1]!!.date)
                    }
                },
                onFailure = {})
        }
    }

//    private fun fetchEvents() {
//        viewModelScope.launch {
//            EventRepository.getEvents(
//                onChange = { events ->
//                    eventsMap = events.toSortedMap { o1, o2 ->
//                        events[o2]!!.date.compareTo(events[o1]!!.date)
//                    }
//                    isLoading = false
//                },
//                onFailure = {
//                    shouldNavigateBack.postValue(true)
//                    isLoading = false
//                }
//            )
//        }
//    }
}