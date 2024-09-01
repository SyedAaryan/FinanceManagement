package com.example.financemanagement.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financemanagement.repository.ReasonRepository
import com.example.financemanagement.repository.SalaryRepository
import com.example.financemanagement.repository.TransactionRepository
import kotlinx.coroutines.launch

class AddTransactionViewModel : ViewModel() {

    var transactionDate: Long? by mutableStateOf(null)
    val selectedReason = mutableStateOf("")
    var transactionAmount by mutableStateOf("")
    var selectedPaymentMethod by mutableStateOf("")
    var selectedMethodTotal by mutableStateOf("")


    var reasonsMap by mutableStateOf(mapOf<String, String>())
        private set

    fun onTransactionDateChange(newDate: Long?){
        transactionDate = newDate
    }



    fun onTransactionAmountChange(newString: String) {
        transactionAmount= newString
    }

    fun onPaymentMethodChange(method: String) {
        selectedPaymentMethod = method
        selectedMethodTotal = when (method) {
            TransactionRepository.TransactionMethod.Cash.toString() -> SalaryRepository.TotalAmount.TotalCash.toString()
            TransactionRepository.TransactionMethod.NetBanking.toString() -> SalaryRepository.TotalAmount.TotalNetBanking.toString()
            else -> ""
        }
    }


    fun addTransaction(onSuccess: () -> Unit, onFailure: () -> Unit){
        viewModelScope.launch {
            val result = TransactionRepository
                .addTransaction(
                    transactionDate?: 0L,
                    selectedReason.value,
                    transactionAmount.toInt(),
                    selectedPaymentMethod,
                    selectedMethodTotal
                )
            if (result.isSuccess) onSuccess()
            else onFailure()
        }
    }

    init {
        fetchReasons()
    }

    private fun fetchReasons() {
        viewModelScope.launch {
            ReasonRepository.getReasons(
                onChange = { reasons ->
                    reasonsMap = reasons
                },
                onFailure = {
                }
            )
        }
    }

}