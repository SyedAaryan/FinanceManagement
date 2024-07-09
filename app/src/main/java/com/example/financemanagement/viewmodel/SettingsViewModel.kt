package com.example.financemanagement.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financemanagement.repository.SalaryRepository
import kotlinx.coroutines.launch

class SettingsViewModel: ViewModel() {

    var salary by mutableStateOf("")

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

}

