package com.example.financemanagement.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financemanagement.model.Transactions
import com.example.financemanagement.repository.HistoryRepository
import com.example.financemanagement.repository.ReasonRepository
import com.example.financemanagement.view.components.dropdown.Timeline
import kotlinx.coroutines.launch

class HistoryViewModel : ViewModel() {

    private val _selectedTimeLine = mutableStateOf("")
    val selectedTimeLine: State<String> get() = _selectedTimeLine

    var historyByDate: Long? by mutableStateOf(null)

    private var selectedPaymentMethod by mutableStateOf("")

    private val _transactionsData = mutableStateOf<Map<String?, Transactions>>(emptyMap())
    val transactionsData: State<Map<String?, Transactions>> get() = _transactionsData

    private val errorLiveData = mutableStateOf<String?>(null)

    var reasonsMap by mutableStateOf(mapOf<String, String>())
        private set

    fun onTimeLineChange(timeline: String) {
        _selectedTimeLine.value = timeline
    }

    fun onPaymentMethodChange(method: String) {
        selectedPaymentMethod = method
    }

    fun onHistoryByDateChange(newDate: Long?) {
        historyByDate = newDate
    }

    fun performAction(string: String){
        when(string){
            Timeline.DATE.toString() -> {
                fetchHistoryByDate()
                fetchReasons()
            }
        }
    }

    private fun fetchHistoryByDate() {
        viewModelScope.launch {
            val result = HistoryRepository.fetchHistoryByDate(
                historyByDate ?: 0L,
                selectedPaymentMethod)

            result.onSuccess { transactions ->
                _transactionsData.value = transactions.entries
                    .sortedByDescending { it.value.date }
                    .associate { it.key to it.value }
                errorLiveData.value = null
            }.onFailure { exception ->
                errorLiveData.value = exception.message
            }
        }
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
}
