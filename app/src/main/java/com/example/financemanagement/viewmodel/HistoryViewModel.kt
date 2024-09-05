package com.example.financemanagement.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class HistoryViewModel: ViewModel() {

    private val _selectedTimeLine = mutableStateOf("")
    val selectedTimeLine: State<String> get() = _selectedTimeLine
    var historyByDate: Long? by mutableStateOf(null)

    private var selectedPaymentMethod by mutableStateOf("")

    fun onTimeLineChange(timeline: String) {
        _selectedTimeLine.value = timeline
    }

    fun onPaymentMethodChange(method: String) {
        selectedPaymentMethod = method
    }

    fun onHistoryByDateChange(newDate: Long?){
        historyByDate = newDate
    }
}
