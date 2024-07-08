package com.example.financemanagement.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.financemanagement.repository.SalaryRepository

class SalaryViewModel : ViewModel() {

    var salary by mutableStateOf("")

    fun onSalaryChange(newSalary: String) {
        salary = newSalary
    }

    fun addSalary(onSuccess: () -> Unit, onFailure: () -> Unit) {
        SalaryRepository.addSalary(salary.toInt(),
            onSuccess = {
                onSuccess()
            },
            onFailure = {
                onFailure()
            }
        )
    }
}