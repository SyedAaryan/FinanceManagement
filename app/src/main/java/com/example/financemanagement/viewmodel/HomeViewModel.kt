package com.example.financemanagement.viewmodel

import android.util.Log
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

    // Private variables to store the salary, totalNetBTransactions and totalCashTransactions
    private var _salary by mutableIntStateOf(0)
    private var _totalNetBTransactions by mutableIntStateOf(0)
    private var _totalCashTransactions by mutableIntStateOf(0)

    // Public variables to get the salary, totalNetBTransactions and totalCashTransactions
    val salary: Int
        get() = _salary

    val totalNetBTransactions: Int
        get() = _totalNetBTransactions

    val totalCashTransactions: Int
        get() = _totalCashTransactions

    // This block of code is executed when the HomeViewModel is initialized
    init {
        fetchSalaryAndTotalMethods()
        checkSalary()
    }

    //This function fetches the salary and total transactions (i,e totalNetB and Total Cash)
    private fun fetchSalaryAndTotalMethods() {
        viewModelScope.launch {
            SalaryRepository.getSalary(
                // If the salary is fetched successfully, the values are stored in the private variables
                onChange = { remaining, cash, netBanking ->
                    _salary = remaining
                    _totalCashTransactions = cash
                    _totalNetBTransactions = netBanking
                },
                // If there is an error while fetching the salary, the values are set to 0
                onFailure = { exception ->
                    Log.e("HomeViewModel", "Error: ${exception.message}")
                    _salary = 0
                    _totalCashTransactions = 0
                    _totalNetBTransactions = 0
                }
            )
        }
    }

    // This function is called when the user signs out
    fun signOut() {
        FirebaseService.auth.signOut()
    }

    //TODO: Check if this works by running a mock

    // This function checks if its been 30 days since the salary was last updated and updates the salary if it has been 30 days
    private fun checkSalary() {
        viewModelScope.launch {
            try {
                val storedDate = SalaryRepository.getSalaryDate()
                val storedLocalDate =
                    Instant.ofEpochMilli(storedDate).atZone(ZoneId.systemDefault()).toLocalDate()
                val currentDate = LocalDate.now()
                if (currentDate >= storedLocalDate.plusDays(30)) {
                    updateSalary()
                }
            } catch (e: Exception) {
                // Handle exception
            }
        }

    }

    // This function is called bu checkSalary() to update the salary when it has been 30 days since the last update
    private fun updateSalary() {
        viewModelScope.launch {
            try {
                val constSalary = SalaryRepository.getConstantSalary()
                SalaryRepository.updateSalaryDateAndRemainingSalary(
                    LocalDate.now().toEpochDay() * 86400000,
                    constSalary
                )
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }
}