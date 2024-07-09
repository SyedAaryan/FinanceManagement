package com.example.financemanagement.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.financemanagement.repository.SalaryRepository
import com.example.financemanagement.services.FirebaseService

class HomeViewModel : ViewModel() {

    var salary by mutableIntStateOf(0)

    init {
        fetchSalary()
    }

    private fun fetchSalary() {
        SalaryRepository.getSalary(
            onChange = {
                salary = it
            },
            onFailure = {

            }
        )
    }

    fun signOut() {
        FirebaseService.auth.signOut()
    }
}