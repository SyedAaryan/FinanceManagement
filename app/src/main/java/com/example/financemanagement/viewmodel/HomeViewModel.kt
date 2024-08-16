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
    var totalNetBTransactions by mutableIntStateOf(0)
    var totalCashTransactions by mutableIntStateOf(0)

    init {
        fetchSalaryAndTotalMethods()
        checkAndUpdateSalary()
    }

    //This function fetches the salary and total transactions (i,e totalNetB and Total Cash)
    private fun fetchSalaryAndTotalMethods() {
        viewModelScope.launch {
            SalaryRepository.getSalary(
                onChange = { remaining, cash, netBanking ->
                    salary = remaining
                    totalCashTransactions = cash
                    totalNetBTransactions = netBanking
                },
                onFailure = { exception ->
                    // Handle exception
                }
            )
        }
    }


    fun signOut() {
        FirebaseService.auth.signOut()
    }

    private fun checkAndUpdateSalary() {
        viewModelScope.launch {
            try {
                val storedDate = SalaryRepository.getSalaryDate()
                val storedLocalDate =
                    Instant.ofEpochMilli(storedDate).atZone(ZoneId.systemDefault()).toLocalDate()
                val currentDate = LocalDate.now()
                if (currentDate >= storedLocalDate.plusDays(30)) {
                    val constSalary = SalaryRepository.getConstantSalary()
                    SalaryRepository.updateSalaryDateAndRemainingSalary(
                        currentDate.toEpochDay() * 86400000,
                        constSalary
                    )
                }
            } catch (e: Exception) {
                // Handle exception
            }
        }

    }

//    private fun getTotalNetBTransactions() {
//        viewModelScope.launch {
//            try {
//                val total = TransactionRepository.getTransactionByMethod(TransactionRepository.TransactionMethod.NetBanking)
//                 totalNetBTransactions = total.toInt()
//            } catch (e: Exception) {
//                // Handle exception
//            }
//        }
//    }
//
//    private fun getTotalCashTransactions() {
//        viewModelScope.launch {
//            try {
//                val total = TransactionRepository.getTransactionByMethod(TransactionRepository.TransactionMethod.Cash)
//                totalCashTransactions = total.toInt()
//            } catch (e: Exception) {
//                // Handle exception
//            }
//        }
//    }
}