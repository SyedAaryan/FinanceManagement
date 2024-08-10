package com.example.financemanagement.repository

import com.example.financemanagement.services.FirebaseService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.tasks.await

object SalaryRepository {

    private val database by lazy { FirebaseService.firebaseDatabase }

    suspend fun addRemainingSalary(salary: Int) {
        val user = FirebaseService.user
        if (user != null) {
            val uid = user.uid
            try {
                database.getReference("Users/$uid/Remaining Salary")
                    .setValue(salary)
                    .await()
            } catch (e: Exception) {
                throw e
            }
        } else {
            throw IllegalStateException("User is not authenticated.")
        }
    }

    fun getSalary(onChange: (Int) -> Unit, onFailure: (Exception) -> Unit){
        val user = FirebaseService.user
        if (user != null) {
            val uid = user.uid
            database.getReference("Users/$uid/Remaining Salary")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val salary = snapshot.getValue(Int::class.java)
                        onChange(salary ?: 0)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        onFailure(Exception("Failed to get salary"))
                    }
                })
        }
    }
}
