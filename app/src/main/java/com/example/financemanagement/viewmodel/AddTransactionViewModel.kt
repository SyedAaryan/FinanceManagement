package com.example.financemanagement.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.financemanagement.repository.TransactionRepository

class AddTransactionViewModel : ViewModel() {

    var transactionDate: Long? by mutableStateOf(null)
    var transactionReason by mutableStateOf("")
    var transactionAmount by mutableStateOf("")

    fun onTransactionDateChange(newDate: Long?){
        transactionDate = newDate
    }

    fun onTransactionReasonChange(newString: String) {
        transactionReason= newString
    }

    fun onTransactionAmountChange(newString: String) {
        transactionAmount= newString
    }

    fun addTransaction(onSuccess: () -> Unit, onFailure: () -> Unit){
        TransactionRepository.addTransaction(transactionDate?: 0L, transactionReason,transactionAmount.toInt(),
            onSuccess = {
                onSuccess()
            },
            onFailure = {
                onFailure()
            }
        )
    }

}