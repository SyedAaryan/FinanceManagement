package com.example.financemanagement.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class HistoryViewModel: ViewModel() {

    private var selectedPaymentMethod by mutableStateOf("")

    fun onPaymentMethodChange(method: String) {
        selectedPaymentMethod = method
    }

}