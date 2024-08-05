package com.example.financemanagement.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financemanagement.repository.ReasonRepository
import com.example.financemanagement.repository.SalaryRepository
import kotlinx.coroutines.launch

class SettingsViewModel: ViewModel() {

    var salary by mutableStateOf("")
    var reason by mutableStateOf("")
    val selectedReasonKey = mutableStateOf("")
    var newReason by mutableStateOf("")


    var reasonsMap by mutableStateOf(mapOf<String, String>())
        private set

    fun addSalary(onSuccess: () -> Unit, onFailure: () -> Unit) {
        viewModelScope.launch {
            try {
                SalaryRepository.addSalary(salary.toInt())
                onSuccess()
            } catch (e: Exception) {
                onFailure()
            }
        }
    }

    fun addReason(onSuccess: () -> Unit, onFailure: () -> Unit){
        viewModelScope.launch {
            try {
                ReasonRepository.addReason(reason)
                onSuccess()
            }catch (e: Exception){
                onFailure()
            }
        }
    }

    fun deleteReason(onSuccess: () -> Unit, onFailure: () -> Unit){
        println("deleteReason called with key: ${selectedReasonKey.value}")
        viewModelScope.launch {
            try {
                ReasonRepository.deleteReason(selectedReasonKey.value)
                onSuccess()
            }catch (e: Exception){
                onFailure()
            }
        }
    }

    fun updateReason(onSuccess: () -> Unit, onFailure: () -> Unit){
        viewModelScope.launch {
            try {
                ReasonRepository.updateReason(selectedReasonKey.value, newReason)
                onSuccess()
            }catch (e: Exception){
                onFailure()
            }
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

