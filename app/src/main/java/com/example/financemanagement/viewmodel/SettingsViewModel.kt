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

    //the function addRemainingSalary and addSalaryDate may be confusing, explanation is at the end of this file
    fun addRemainingSalary(onSuccess: () -> Unit, onFailure: () -> Unit) {
        viewModelScope.launch {
            try {
                SalaryRepository.addRemainingSalary(salary.toInt())
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

/*syedaaryan explanation(10/08/2024):
*     So i was working on the function where the user can add the salaryDate in the DB, so that when
* the same date arrives next month, the salary resets, before this the salary was pushed as "salary"
* in teh DB, but i have decided to change the structure a bit
* addRemainingSalary is the function that pushes the salary as "remainingSalary "in the DB, and
* addSalaryDate is the function that salary and salaryDate in the DB. And this will remain constant,
* but the "remaining salary" will be
* Ill also be using only one variable as "salary" in the settinsgView and SettinsgVM to make it easier
* This may change in the other VMs
* Idk if this is smart or an idiotic move, lets see how it works, change the counter
* Its an smart move : 1
* Its an idiotic move : 0
* */


