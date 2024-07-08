package com.example.financemanagement.repository

import com.example.financemanagement.model.Salary
import com.example.financemanagement.services.FirebaseService
import com.google.firebase.auth.FirebaseAuth

object SalaryRepository {

    private val database by lazy { FirebaseService.firebaseDatabase }
    private val auth by lazy { FirebaseAuth.getInstance() }

    fun addSalary(salary: Int, onSuccess: (key: String) -> Unit, onFailure: (Exception) -> Unit) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val uid = currentUser.uid
            val key = database.getReference("User/$uid/salary").push().key ?: ""
            database.getReference("User/$uid/salary/$key")
                .setValue(Salary(salary))
                .addOnSuccessListener {
                    onSuccess(key)
                }
                .addOnFailureListener { e ->
                    onFailure(e)
                }
        } else {
            onFailure(Exception("User not authenticated"))
        }
    }
}
