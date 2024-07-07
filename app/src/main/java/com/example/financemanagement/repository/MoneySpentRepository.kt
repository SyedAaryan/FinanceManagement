package com.example.financemanagement.repository

import com.example.financemanagement.model.Salary
import com.example.financemanagement.services.FirebaseService
import com.example.financemanagement.services.FirebaseService.getUserDatabaseReference
import kotlinx.coroutines.tasks.await

object MoneySpentRepository {

    private val database by lazy { FirebaseService.firebaseDatabase }


    fun addSalary(salary: Int, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val userDatabaseRef = getUserDatabaseReference()
        val salaryRef = userDatabaseRef.child("salary")

        salaryRef.setValue(Salary(salary))
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }

    suspend fun getSalary(): Salary? {
        val userDatabaseRef = getUserDatabaseReference()
        val salaryRef = userDatabaseRef.child("salary")

        return salaryRef.get().await().getValue(Salary::class.java)
    }
}
