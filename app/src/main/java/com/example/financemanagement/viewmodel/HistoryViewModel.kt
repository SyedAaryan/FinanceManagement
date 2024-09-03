package com.example.financemanagement.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HistoryViewModel: ViewModel() {

    private val _selectedTimeLine = MutableLiveData<String>()
    val selectedTimeLine: LiveData<String> get() = _selectedTimeLine

    private var selectedPaymentMethod by mutableStateOf("")

    fun onTimeLineChange(timeline: String) {
        _selectedTimeLine.value = timeline
    }

    fun onPaymentMethodChange(method: String) {
        selectedPaymentMethod = method
    }

}