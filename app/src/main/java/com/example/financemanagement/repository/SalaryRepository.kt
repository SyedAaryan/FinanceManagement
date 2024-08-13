package com.example.financemanagement.repository


import com.example.financemanagement.services.FirebaseService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.tasks.await

object SalaryRepository {

    private val database by lazy { FirebaseService.firebaseDatabase }

    suspend fun addRemainingSalary(salary: Int) {
        val user = FirebaseService.user ?: throw IllegalStateException("User is not authenticated.")
        val uid = user.uid
        try {
            database.getReference("Users/$uid/Salary/Remaining")
                .setValue(salary)
                .await()
        } catch (e: Exception) {
            throw e
        }

    }

    suspend fun addSalaryDate(salary: Int,date: Long) {
        val user = FirebaseService.user ?: throw IllegalStateException("User is not authenticated.")
        val uid = user.uid
        try {
            database.getReference("Users/$uid/Salary")
                .updateChildren(mapOf("Initial Salary" to salary, "Date" to date) )
                .await()
        } catch (e: Exception) {
            throw e
        }
    }

    fun getSalary(onChange: (Int) -> Unit, onFailure: (Exception) -> Unit){
        val user = FirebaseService.user
        if (user != null) {
            val uid = user.uid
            database.getReference("Users/$uid/Salary/Remaining")
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

    suspend fun getSalaryDate(): Long {
        val user = FirebaseService.user ?: throw IllegalStateException("User is not authenticated.")
        val uid = user.uid
        try {
            val dateMillis = database.getReference("Users/$uid/Salary/Date")
                .get().
                await()
                .getValue(Long::class.java) ?: 0L
            return dateMillis
        } catch (e: Exception) {
            throw e
        }

    }

    //This function is used to get the Initial salary that is stored in the "Salary" from the DB
    suspend fun getConstantSalary(): Int {
        val user = FirebaseService.user ?: throw IllegalStateException("User is not authenticated.")
        val uid = user.uid
        try {
            val constSalary = database.getReference("Users/$uid/Salary/Initial Salary")
                .get()
                .await()
                .getValue(Int::class.java) ?: 0
            return constSalary

        } catch (e: Exception) {
            throw e
        }
    }


    suspend fun updateSalaryDateAndRemainingSalary(newDate: Long, newAmount: Int) {
        val user = FirebaseService.user ?: throw IllegalStateException("User is not authenticated.")
        val uid = user.uid
        try {
            database.getReference("Users/$uid/Salary")
                .updateChildren(mapOf("Date" to newDate, "Remaining" to newAmount))
                .await()
        } catch (e: Exception) {
            throw e
        }
    }
}
