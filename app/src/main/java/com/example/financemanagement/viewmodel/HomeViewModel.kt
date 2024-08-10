package com.example.financemanagement.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financemanagement.repository.SalaryRepository
import com.example.financemanagement.services.FirebaseService
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class HomeViewModel : ViewModel() {

    var salary by mutableIntStateOf(0)

    init {
        fetchSalary()
        checkAndUpdateSalary()

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

    private fun checkAndUpdateSalary() {
        viewModelScope.launch {
            try {
                val storedDate = SalaryRepository.getSalaryDate()
                val storedLocalDate = Instant.ofEpochMilli(storedDate).atZone(ZoneId.systemDefault()).toLocalDate()
                val currentDate = LocalDate.now()
                if (currentDate >= storedLocalDate.plusDays(30)) {
                    val constSalary = SalaryRepository.getConstantSalary()
                    SalaryRepository.updateSalaryDateAndRemainingSalary(currentDate.toEpochDay() * 86400000, constSalary)
                }
            } catch (e: Exception) {
                // Handle exception
            }
        }

    }

}